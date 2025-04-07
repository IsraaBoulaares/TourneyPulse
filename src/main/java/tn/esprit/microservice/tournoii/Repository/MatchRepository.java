package tn.esprit.microservice.tournoii.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microservice.tournoii.entity.match;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<match, Long> {
    List<match> findByDateMatchBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
