package tn.esprit.microservice.tournoii.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.tournoii.entity.match;
import tn.esprit.microservice.tournoii.Repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    public List<match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public match createMatch(match match) {
        return matchRepository.save(match);
    }

    public match updateMatch(Long id, match matchDetails) {
        return matchRepository.findById(id)
                .map(match -> {
                    match.setNomMatch(matchDetails.getNomMatch());
                    match.setDateMatch(matchDetails.getDateMatch());
                    match.setDuree(matchDetails.getDuree());
                    match.setTournoi(matchDetails.getTournoi());

                    return matchRepository.save(match);
                })
                .orElseThrow(() -> new RuntimeException("Match non trouvé"));
    }

    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new RuntimeException("Match non trouvé pour suppression");
        }
        matchRepository.deleteById(id);
    }

    public List<match> getMatchsByDate(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findByDateMatchBetween(start, end);
    }

}
