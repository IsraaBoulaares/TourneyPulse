package com.user.user.auth;


import com.user.user.email.EmailService;
import com.user.user.email.EmailTemplateName;
import com.user.user.role.RoleRepository;
import com.user.user.security.JwtService;
import com.user.user.user.Token;
import com.user.user.user.TokenRepository;
import com.user.user.user.User;
import com.user.user.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("ROLE USER was not initialized"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user, false);  // false = activation token
        String confirmationUrl = activationUrl + "?token=" + newToken;
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                confirmationUrl,
                newToken,
                "Account Activation"
        );
        log.info("Validation email sent to: {}", user.getEmail());
    }

    // New forgot password method
    public void forgotPassword(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        sendPasswordResetEmail(user);
    }

    private void sendPasswordResetEmail(User user) throws MessagingException {
        var resetToken = generateAndSaveActivationToken(user, true);  // true = reset token
        String resetUrl = activationUrl.replace("activate-account", "reset-password") + "?token=" + resetToken;
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.RESET_PASSWORD,
                resetUrl,
                resetToken,
                "Reset Your Password"
        );
        log.info("Password reset email sent to: {}", user.getEmail());
    }

    // Modified token generation to distinguish reset tokens
    private String generateAndSaveActivationToken(User user, boolean isResetToken) {
        String generatedToken = isResetToken ? "RESET-" + generateActivationCode(6) : generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (token.startsWith("RESET-")) {
            throw new RuntimeException("This is a password reset token, not an activation token");
        }
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    // New reset password method
    public void resetPassword(String token, String newPassword) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));
        if (!token.startsWith("RESET-")) {
            throw new RuntimeException("This is not a password reset token");
        }
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendPasswordResetEmail(savedToken.getUser());
            throw new RuntimeException("Reset token has expired. A new token has been sent to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }


    // New update user method
    public void updateUser(String email, UpdateUserRequest request) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (request.getFirstname() != null && !request.getFirstname().isEmpty()) {
            user.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null && !request.getLastname().isEmpty()) {
            user.setLastname(request.getLastname());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty() && !request.getEmail().equals(email)) {
            // Check if the new email is already taken
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already in use: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
            user.setEnabled(false); // Require re-activation if email changes
            try {
                sendValidationEmail(user);
            } catch (MessagingException e) {
                log.error("Failed to send re-activation email to: {}", user.getEmail(), e);
                throw new RuntimeException("Failed to send re-activation email", e);
            }
        }
        userRepository.save(user);
        log.info("User updated successfully: {}", user.getEmail());
    }

    @Transactional
    public void deleteUser(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        tokenRepository.deleteByUser(user);
        userRepository.delete(user);
        log.info("User deleted successfully: {}", email);
    }
}
