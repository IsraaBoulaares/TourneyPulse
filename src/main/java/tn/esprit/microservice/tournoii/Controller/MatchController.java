package tn.esprit.microservice.tournoii.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.tournoii.Service.EmailService;
import tn.esprit.microservice.tournoii.Service.MatchService;
import tn.esprit.microservice.tournoii.entity.match;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;
@Autowired
private EmailService emailService;
    @GetMapping("getAll")
    public List<match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<match> getMatchById(@PathVariable Long id) {
        Optional<match> matchOptional = matchService.getMatchById(id);
        if (matchOptional.isPresent()) {
            return ResponseEntity.ok(matchOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<match> createMatch(@RequestBody match match) {
        match createdMatch = matchService.createMatch(match);
        return ResponseEntity.ok(createdMatch);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<match> updateMatch(@PathVariable Long id, @RequestBody match matchDetails) {
        try {
            match updatedMatch = matchService.updateMatch(id, matchDetails);
            return ResponseEntity.ok(updatedMatch);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/by-date")
    public ResponseEntity<List<match>> getMatchsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<match> matchs = matchService.getMatchsByDate(startOfDay, endOfDay);
        return ResponseEntity.ok(matchs);
    }

    @PostMapping("/{id}/notifier")
    public ResponseEntity<String> notifierAvantMatch(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        Optional<match> matchOpt = matchService.getMatchById(id);
        if (matchOpt.isEmpty()) return ResponseEntity.notFound().build();

        match match = matchOpt.get();

        String message = request.get("message");
        String type = request.get("type");

        if ("email".equalsIgnoreCase(type)) {
            String destinataire = "boularesisraa@gmail.com";
            emailService.envoyerEmail(destinataire, "Rappel - Match : " + match.getNomMatch(), message);
            return ResponseEntity.ok("Notification envoyée par email.");
        }

        return ResponseEntity.badRequest().body("Type de notification non supporté.");
    }

}