import { Component, OnInit } from '@angular/core';
import { StadeService } from '../../../services/stade.service';
import { Stade } from '../../models/stade.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stade',
  templateUrl: './stade.component.html',
  styleUrls: ['./stade.component.css']
})
export class StadeComponent implements OnInit {
  stades: Stade[] = [];
  filteredStades: Stade[] = [];
  paginatedStades: Stade[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;
  searchQuery: string = '';
  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = 1;

  constructor(private stadeService: StadeService, private router: Router) {}

  ngOnInit(): void {
    this.loadStades();
  }

  loadStades(): void {
    this.stadeService.getAllStades().subscribe({
      next: (stades) => {
        this.stades = stades;
        this.filterStades();
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des stades';
        console.error(error);
      }
    });
  }

  filterStades(): void {
    this.filteredStades = this.stades.filter(stade => {
      return !this.searchQuery ||
        stade.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        stade.location.toLowerCase().includes(this.searchQuery.toLowerCase());
    });
    this.updatePagination();
  }

  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredStades.length / this.pageSize);
    this.currentPage = Math.min(this.currentPage, this.totalPages || 1);
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedStades = this.filteredStades.slice(start, end);
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

  onAddStade(): void {
    this.router.navigate(['/backoffice/stade/add']);
  }

  onEditStade(id: number): void {
    this.router.navigate([`/backoffice/stades/edit/${id}`]);
  }

  onDeleteStade(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce stade ?')) {
      this.stadeService.deleteStade(id).subscribe({
        next: () => {
          this.successMessage = 'Stade supprimé avec succès';
          this.loadStades();
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression du stade';
          console.error(error);
        }
      });
    }
  }

  downloadExcel(): void {
    this.stadeService.exportToExcel().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'stades.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du téléchargement du fichier Excel';
        console.error(error);
      }
    });
  }
}
