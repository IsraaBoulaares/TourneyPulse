package tn.esprit.microservice.tournoii.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microservice.tournoii.entity.match;

public interface MatchRepository extends JpaRepository<match, Long> {
}
