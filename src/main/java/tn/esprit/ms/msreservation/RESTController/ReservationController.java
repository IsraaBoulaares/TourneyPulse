package tn.esprit.ms.msreservation.RESTController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ms.msreservation.Service.ReservationService;
import tn.esprit.ms.msreservation.entities.Reservation;

import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return service.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return service.saveReservation(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
    }
}
