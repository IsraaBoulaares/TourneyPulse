package tn.esprit.gestionequipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionequipe.entities.Users;


public interface UserRepository extends JpaRepository<Users, Long> {
}