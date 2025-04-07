package tn.esprit.microservice.tournoii.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.tournoii.Repository.TournoiRepository;
import tn.esprit.microservice.tournoii.entity.Tournoi;
import tn.esprit.microservice.tournoii.entity.match;

import java.util.List;
import java.util.Optional;

@Service
public class TournoiService {

    @Autowired
    private TournoiRepository tournoiRepository;

    public List<Tournoi> getAllTournois() {
        return tournoiRepository.findAll();
    }

    public Optional<Tournoi> getTournoiById(Long id) {
        return tournoiRepository.findById(id);
    }

    public Tournoi createTournoi(Tournoi tournoi) {
        return tournoiRepository.save(tournoi);
    }

    public Tournoi updateTournoi(Long id, Tournoi tournoiDetails) {
        return tournoiRepository.findById(id)
                .map(tournoi -> {
                    tournoi.setNom(tournoiDetails.getNom());
                    tournoi.setLieu(tournoiDetails.getLieu());
                    tournoi.setDate(tournoiDetails.getDate());
                    tournoi.setClassement(tournoiDetails.getClassement());

                    return tournoiRepository.save(tournoi);
                })
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé"));
    }

    public void deleteTournoi(Long id) {
        if (!tournoiRepository.existsById(id)) {
            throw new RuntimeException("Tournoi non trouvé pour suppression");
        }
        tournoiRepository.deleteById(id);
    }
    public match ajouterMatchAuTournoi(Long tournoiId, match match) {
        Tournoi tournoi = tournoiRepository.findById(tournoiId)
                .orElseThrow(() -> new RuntimeException("Tournoi non trouvé"));
        match.setTournoi(tournoi);
        tournoi.getMatches().add(match);
        tournoiRepository.save(tournoi);
        return match;
    }
}
