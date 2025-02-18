package tn.esprit.ms.msreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ms.msreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
