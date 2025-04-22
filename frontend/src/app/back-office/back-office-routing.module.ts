import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationListComponent } from './dashboard/reservation-list/reservation-list.component';
import { AddReservationComponent } from './dashboard/add-reservation/add-reservation.component';
import { BackOfficeComponent } from './back-office.component';
import { TournoiComponent } from './dashboard/tournoi/tournoi.component';
import { AddTournoiComponent } from './dashboard/tournoi/add/add.component';
import { StadeComponent } from './dashboard/stade/stade.component';
import { AddStadeComponent } from './dashboard/stade/add/add.component';
import { AddEquipeComponent } from './dashboard/equipe/add/add.component';
import { EquipeComponent } from './dashboard/equipe/equipe.component';
const routes: Routes = [
  {
    path: '',
    component: BackOfficeComponent,
    children: [
      { path: 'reservations', component: ReservationListComponent },
      { path: 'reservations/add', component: AddReservationComponent },
      { path: 'tournois', component: TournoiComponent },
      { path: 'tournois/add', component: AddTournoiComponent },
      { path: 'stade', component: StadeComponent },
      { path: 'stade/add', component: AddStadeComponent },
      { path: 'equipe', component: EquipeComponent },
      { path: 'equipe/add', component: AddEquipeComponent },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        component: DashboardComponent
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BackOfficeRoutingModule { }
