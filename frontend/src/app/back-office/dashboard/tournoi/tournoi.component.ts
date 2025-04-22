import { Component, OnInit } from '@angular/core';
import { TournoiService } from '../../../services/tournoi.service';
import { Tournoi } from '../../../models/tournoi.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tournoi',
  templateUrl: '../tournoi/tournoi.component.html',
  styleUrls: ['../tournoi/tournoi.component.css']
})
export class TournoiComponent implements OnInit {
  tournois: Tournoi[] = [];
  filteredTournois: Tournoi[] = [];
  paginatedTournois: Tournoi[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;
  classementFilter: string = '';
  searchQuery: string = '';
  currentPage: number = 1;
  pageSize: number = 5; // Nombre de tournois par page
  totalPages: number = 1;

  constructor(private tournoiService: TournoiService, private router: Router) {}

  ngOnInit(): void {
    this.loadTournois();
  }

  loadTournois(): void {
    this.tournoiService.getAllTournois().subscribe({
      next: (tournois) => {
        this.tournois = tournois;
        this.filterTournois();
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des tournois';
        console.error(error);
      }
    });
  }

  filterTournois(): void {
    this.filteredTournois = this.tournois.filter(tournoi => {
      const matchesClassement = !this.classementFilter || tournoi.classement === this.classementFilter;
      const matchesSearch = !this.searchQuery ||
        tournoi.nom.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        tournoi.lieu.toLowerCase().includes(this.searchQuery.toLowerCase());
      return matchesClassement && matchesSearch;
    });
    this.updatePagination();
  }

  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredTournois.length / this.pageSize);
    this.currentPage = Math.min(this.currentPage, this.totalPages || 1);
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedTournois = this.filteredTournois.slice(start, end);
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

  onAddTournoi(): void {
    this.router.navigate(['/backoffice/tournois/add']);
  }

  onDeleteTournoi(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce tournoi ?')) {
      this.tournoiService.deleteTournoi(id).subscribe({
        next: () => {
          this.successMessage = 'Tournoi supprimé avec succès';
          this.loadTournois();
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression du tournoi';
          console.error(error);
        }
      });
    }
  }
}
