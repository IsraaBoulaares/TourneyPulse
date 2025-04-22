import { Component, OnInit } from '@angular/core';
import { EquipeService } from '../../../services/equipe.service';
import { Equipe } from '../../../models/equipe.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-equipe',
  templateUrl: '../add/add.component.html',
  styleUrls: ['../add/add.component.css']
})
export class AddEquipeComponent implements OnInit {
  equipe: Equipe = {
    id: undefined,
    teamName: '',
    homeStadium: '',
    foundedDate: '',
    status: 'ACTIVE',
    league: '',
    teamColors: '',
    squadSizeLimit: 25
  };

  isEditMode: boolean = false;
  errorMessage: string | null = null;

  constructor(
    private equipeService: EquipeService,
    public router: Router, // <-- rendu public si utilisé directement dans le HTML (optionnel si tu utilises goBack())
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.equipeService.getequipeById(+id).subscribe({
        next: (equipe) => {
          this.equipe = equipe;
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors du chargement de l\'équipe';
          console.error(error);
        }
      });
    }
  }

  // Méthode appelée dans le HTML pour revenir en arrière
  goBack() {
    this.router.navigate(['/backoffice/equipe']);
  }

  onSubmit(): void {
    if (this.isEditMode && this.equipe.id) {
      this.equipeService.updateequipe(this.equipe.id, this.equipe).subscribe({
        next: () => {
          this.router.navigate(['/backoffice/equipe']);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la mise à jour de l\'équipe';
          console.error(error);
        }
      });
    } else {
      this.equipeService.createequipe(this.equipe).subscribe({
        next: () => {
          this.router.navigate(['/backoffice/equipe']);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de l\'ajout de l\'équipe';
          console.error(error);
        }
      });
    }
  }
}
