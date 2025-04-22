import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ReservationService } from '../../services/reservation.service';
import { Reservation } from '../../models/reservation.model';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css']
})
export class AddReservationComponent {
  reservation: Reservation = {
    id: 0,
    nom: '',
    date: '',
    idStade: 0,
    idPremiereEquipe: 0,
    idDeuxiemeEquipe: 0,
    idOrganisateur: 0,
    qrCodeBase64: ''
  };

  constructor(
    private reservationService: ReservationService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.reservationService.addReservation(this.reservation).subscribe({
      next: () => {
        alert('Réservation ajoutée avec succès !');
        this.router.navigate(['/reservations']);
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de la réservation:', err);
        alert('Erreur lors de l\'ajout de la réservation.');
      }
    });
  }
}
