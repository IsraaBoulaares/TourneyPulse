import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { Reservation } from '../../models/reservation.model';
import { Router } from '@angular/router';
@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];
  filteredReservations: Reservation[] = [];
  paginatedReservations: Reservation[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;
  statusFilter: string = '';
  searchQuery: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 3; // Changed to 3 to show 3 rows per page
  totalPages: number = 1;

  constructor(private reservationService: ReservationService, private router: Router) {}

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.reservationService.getAllReservations().subscribe(
      (reservations: Reservation[]) => {
        this.reservations = reservations;
        this.currentPage = 1; // Reset to first page
        this.filterReservations();
      },
      (error) => {
        this.errorMessage = 'Erreur lors du chargement des réservations : ' + error.message;
        console.error('Erreur lors du chargement des réservations:', error);
      }
    );
  }

  filterReservations(): void {
    let filtered = [...this.reservations];

    // Filter by status
    if (this.statusFilter) {
      filtered = filtered.filter(reservation => reservation.status === this.statusFilter);
    }

    // Filter by search query (name)
    if (this.searchQuery) {
      filtered = filtered.filter(reservation =>
        reservation.nom.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }

    this.filteredReservations = filtered;
    this.currentPage = 1; // Reset to the first page when filtering
    this.updatePagination();
  }

  updatePagination(): void {
    // Calculate the total number of pages
    this.totalPages = Math.ceil(this.filteredReservations.length / this.itemsPerPage);

    // Ensure the current page is valid
    if (this.currentPage > this.totalPages) {
      this.currentPage = this.totalPages || 1;
    }

    // Calculate paginated reservations
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedReservations = this.filteredReservations.slice(startIndex, endIndex);
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  onStatusChange(event: Event, reservation: Reservation): void {
    const target = event.target as HTMLSelectElement;
    if (target) {
      const newStatus = target.value as 'CONFIRMED' | 'PENDING' | 'CANCELLED'; // Ensure valid status type
      if (newStatus) {
        const confirmChange = window.confirm(
          `Êtes-vous sûr de vouloir changer le statut de la réservation de "${reservation.nom}" (ID: ${reservation.id}) à "${newStatus === 'CONFIRMED' ? 'Confirmé' : newStatus === 'PENDING' ? 'En attente' : 'Annulé'}" ?`
        );
        if (confirmChange) {
          this.updateStatus(reservation, newStatus);
        } else {
          target.value = reservation.status || ''; // Reset to current or empty value
        }
      }
    }
  }

  updateStatus(reservation: Reservation, newStatus: 'CONFIRMED' | 'PENDING' | 'CANCELLED'): void {
    const updatedReservation: Reservation = { ...reservation, status: newStatus };

    // Ensure the ID is defined before calling the updateReservation method
    if (updatedReservation.id !== undefined) {
      this.reservationService.updateReservation(updatedReservation.id, updatedReservation).subscribe({
        next: () => {
          this.successMessage = `Statut de la réservation de "${reservation.nom}" (ID: ${reservation.id}) mis à jour avec succès !`;
          reservation.status = newStatus; // Update the status locally
          this.filterReservations(); // Refetch the filtered results
          setTimeout(() => {
            this.successMessage = null; // Clear success message after 3 seconds
          }, 3000);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la mise à jour du statut : ' + error.message;
          console.error('Erreur lors de la mise à jour du statut:', error);
        }
      });
    } else {
      this.errorMessage = 'Erreur : L\'ID de la réservation est manquant.';
      console.error('ID de la réservation est manquant');
    }
  }

  onAddReservation(): void {
    this.router.navigate(['/backoffice/reservations/add']);
  }


  onDeleteReservation(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette réservation ?')) {
      this.reservationService.deleteReservation(id).subscribe({
        next: () => {
          this.successMessage = 'Réservation supprimée avec succès.';
          this.loadReservations(); // Recharge la liste
        },
        error: () => {
          this.errorMessage = 'Erreur lors de la suppression.';
        }
      });
    }
  }

}
