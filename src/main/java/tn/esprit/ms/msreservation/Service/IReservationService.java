package tn.esprit.ms.msreservation.Service;

import tn.esprit.ms.msreservation.entities.Reservation;

import java.util.List;

public interface IReservationService {
    public List<Reservation> getAllReservations();
    public Reservation getReservationById(Long id);
    public Reservation saveReservation(Reservation reservation);
    public void deleteReservation(Long id);
}
