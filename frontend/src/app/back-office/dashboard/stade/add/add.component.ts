import { Component, OnInit } from '@angular/core';
import { StadeService } from '../../../../services/stade.service';
import { Stade } from '../../../models/stade.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-stade',
  templateUrl: '../add/add.component.html',
  styleUrls: ['../add/add.component.css']
})
export class AddStadeComponent implements OnInit {
  stade: Stade = {
    name: '',
    location: '',
    capacity: 0
  };
  isEditMode: boolean = false;

  constructor(
    private stadeService: StadeService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.stadeService.getStadeById(+id).subscribe({
        next: (stade) => this.stade = stade,
        error: (error) => console.error('Erreur lors du chargement du stade', error)
      });
    }
  }

  onSubmit(): void {
    if (this.isEditMode && this.stade.id) {
      this.stadeService.updateStade(this.stade.id, this.stade).subscribe({
        next: () => {
          this.router.navigate(['/backoffice/stades']);
        },
        error: (error) => console.error('Erreur lors de la mise à jour du stade', error)
      });
    } else {
      this.stadeService.createStade(this.stade).subscribe({
        next: () => {
          this.router.navigate(['/backoffice/stades']);
        },
        error: (error) => console.error('Erreur lors de l\'ajout du stade', error)
      });
    }
  }
}
