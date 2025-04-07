package tn.esprit.gestionequipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionequipe.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}