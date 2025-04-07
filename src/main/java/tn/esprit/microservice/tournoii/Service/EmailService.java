package tn.esprit.microservice.tournoii.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmail(String to, String sujet, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("boularesisraa@gmail.com"); // Add this line with a valid sender email
        email.setTo(to);
        email.setSubject(sujet);
        email.setText(message);
        mailSender.send(email);
    }
}