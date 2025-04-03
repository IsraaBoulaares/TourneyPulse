package tn.esprit.microservice.tournoi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.microservice.tournoi.entity.Tournoi;

public interface TournoiRepository extends JpaRepository<Tournoi,Long> {
}
