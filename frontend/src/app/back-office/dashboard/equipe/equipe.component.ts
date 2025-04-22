import { Component, OnInit } from '@angular/core';
import { EquipeService } from '../../../services/equipe.service';
import { Equipe } from '../../../models/equipe.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipe',
  templateUrl: './equipe.component.html',
  styleUrls: ['./equipe.component.css']
})
export class EquipeComponent implements OnInit {
  equipes: Equipe[] = [];
  filteredEquipes: Equipe[] = [];
  paginatedEquipes: Equipe[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;
  statusFilter: string = '';
  searchQuery: string = '';
  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = 1;

  constructor(private equipeService: EquipeService, private router: Router) {}

  ngOnInit(): void {
    this.loadEquipes();
  }

  loadEquipes(): void {
    this.equipeService.getAllequipes().subscribe({
      next: (equipes) => {
        this.equipes = equipes; // No Date conversion; keep as strings
        this.filterEquipes();
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des équipes';
        console.error(error);
      }
    });
  }

  filterEquipes(): void {
    this.filteredEquipes = this.equipes.filter(equipe => {
      const matchesStatus = !this.statusFilter || equipe.status === this.statusFilter;
      const matchesSearch = !this.searchQuery ||
        equipe.teamName.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        (equipe.homeStadium && equipe.homeStadium.toLowerCase().includes(this.searchQuery.toLowerCase()));
      return matchesStatus && matchesSearch;
    });
    this.updatePagination();
  }

  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredEquipes.length / this.pageSize);
    this.currentPage = Math.min(this.currentPage, this.totalPages || 1);
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedEquipes = this.filteredEquipes.slice(start, end);
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

  onAddEquipe(): void {
    this.router.navigate(['/backoffice/equipe/add']);
  }

  onEditEquipe(id: number): void {
    this.router.navigate([`/backoffice/equipe/edit/${id}`]);
  }

  onDeleteEquipe(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette équipe ?')) {
      this.equipeService.deleteequipe(id).subscribe({
        next: () => {
          this.successMessage = 'Équipe supprimée avec succès';
          this.loadEquipes();
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression de l\'équipe';
          console.error(error);
        }
      });
    }
  }

  onViewPlayers(id: number): void {
    this.router.navigate([`/backoffice/equipe/${id}/players`]);
  }

  downloadPDF(): void {
    this.equipeService.exportToPDF().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'equipes_report.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du téléchargement du PDF';
        console.error(error);
      }
    });
  }
}
