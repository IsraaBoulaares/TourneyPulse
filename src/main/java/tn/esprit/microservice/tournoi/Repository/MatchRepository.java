package tn.esprit.microservice.tournoi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microservice.tournoi.entity.match;

public interface MatchRepository extends JpaRepository<match, Long> {
}
