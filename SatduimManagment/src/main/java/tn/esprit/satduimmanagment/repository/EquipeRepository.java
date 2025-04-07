package tn.esprit.satduimmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.satduimmanagment.entities.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}