package tn.esprit.microservice.tournoii.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.tournoii.Service.MatchService;
import tn.esprit.microservice.tournoii.entity.match;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity<match> createMatch(@RequestBody match match) {
        match createdMatch = matchService.createMatch(match);
        return ResponseEntity.ok(createdMatch);
    }

    @PutMapping("/{id}")
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
}