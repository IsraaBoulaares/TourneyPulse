package tn.esprit.microservice.tournoii.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.tournoii.Service.StatiqueService;
import tn.esprit.microservice.tournoii.entity.statiques;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatiquesController {

    @Autowired
    private StatiqueService service;

    @PostMapping
    public ResponseEntity<statiques> ajouterStat(@RequestBody statiques stat) {
        return new ResponseEntity<>(service.saveStat(stat), HttpStatus.CREATED);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<statiques> getStatParMatch(@PathVariable Long matchId) {
        return service.getByMatchId(matchId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<statiques> getToutesStats() {
        return service.getAllStats();
    }
}
