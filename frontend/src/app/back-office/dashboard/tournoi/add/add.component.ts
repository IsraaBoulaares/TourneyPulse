import { Component } from '@angular/core';
import { TournoiService } from '../../../services/tournoi.service';
import { Tournoi } from '../../../models/tournoi.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-tournoi',
  templateUrl: '../add/add.component.html',
  styleUrls: ['../add/add.component.css']
})
export class AddTournoiComponent {
  tournoi: Tournoi = {
    nom: '',
    lieu: '',
    date: '',
    classement: ''
  };

  constructor(private tournoiService: TournoiService, private router: Router) {}

  onSubmit(): void {
    this.tournoiService.addTournoi(this.tournoi).subscribe({
      next: (response) => {
        console.log('Tournoi ajouté avec succès !', response);
        this.router.navigate(['/backoffice/tournois']);
      },
      error: (error) => {
        console.error('Erreur lors de l\'ajout du tournoi:', error);
        if (error.error) {
          console.error('Détail backend :', error.error);
        }
      }
    });
  }
}
