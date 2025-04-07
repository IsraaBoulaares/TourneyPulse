package tn.esprit.microservice.tournoii.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.tournoii.Repository.StatiquesRepository;
import tn.esprit.microservice.tournoii.entity.statiques;

import java.util.List;
import java.util.Optional;

@Service
    public class StatiqueService {

        @Autowired
        private StatiquesRepository repo;

        public statiques saveStat(statiques stat) {
            return repo.save(stat);
        }

        public Optional<statiques> getByMatchId(Long matchId) {
            return repo.findByMatchId(matchId);
        }

        public List<statiques> getAllStats() {
            return repo.findAll();
        }
    }


