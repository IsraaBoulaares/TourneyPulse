package tn.esprit.microservice.tournoii.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.tournoii.Service.TournoiService;
import tn.esprit.microservice.tournoii.entity.Tournoi;
import tn.esprit.microservice.tournoii.entity.match;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/tournois")
public class TournoiController {

    @Autowired
    private TournoiService tournoiService;

    @GetMapping
    public List<Tournoi> getAllTournois() {
        return tournoiService.getAllTournois();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournoi> getTournoiById(@PathVariable Long id) {
        Optional<Tournoi> tournoi = tournoiService.getTournoiById(id);
        if (tournoi.isPresent()) {
            return ResponseEntity.ok(tournoi.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Tournoi> createTournoi(@RequestBody Tournoi tournoi) {
        Tournoi createdTournoi = tournoiService.createTournoi(tournoi);
        return ResponseEntity.ok(createdTournoi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournoi> updateTournoi(@PathVariable Long id, @RequestBody Tournoi tournoiDetails) {
        try {
            Tournoi updatedTournoi = tournoiService.updateTournoi(id, tournoiDetails);
            return ResponseEntity.ok(updatedTournoi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournoi(@PathVariable Long id) {
        try {
            tournoiService.deleteTournoi(id);
            // 204 No Content : suppression OK
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Par exemple, si "Tournoi non trouvé" est levé
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{tournoiId}/matches")
    public ResponseEntity<match> ajouterMatchAuTournoi(
            @PathVariable Long tournoiId,
            @RequestBody match match) {

        match nouveauMatch = tournoiService.ajouterMatchAuTournoi(tournoiId, match);
        return ResponseEntity
                .created(URI.create("/matches/" + nouveauMatch.getIdMatch()))
                .body(nouveauMatch);
    }
}