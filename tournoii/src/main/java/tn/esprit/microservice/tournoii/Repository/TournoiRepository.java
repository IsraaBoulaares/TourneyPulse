package tn.esprit.microservice.tournoii.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microservice.tournoii.entity.Tournoi;

public interface TournoiRepository extends JpaRepository<Tournoi,Long> {
}
