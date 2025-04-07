package tn.esprit.microservice.tournoii.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.tournoii.entity.statiques;

import java.util.Optional;


@Repository
public interface StatiquesRepository extends JpaRepository<statiques, Long> {
    Optional<statiques> findByMatchId(Long matchId);
}
