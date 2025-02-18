package tn.esprit.ms.msreservation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ms.msreservation.entities.Reservation;
import tn.esprit.ms.msreservation.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private ReservationRepository repository;

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        repository.deleteById(id);
    }
}
