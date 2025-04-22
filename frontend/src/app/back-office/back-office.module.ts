import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BackOfficeRoutingModule } from './back-office-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReservationListComponent } from './dashboard/reservation-list/reservation-list.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AddReservationComponent } from './dashboard/add-reservation/add-reservation.component';
import { TournoiComponent } from './dashboard/tournoi/tournoi.component';
import { AddTournoiComponent } from './dashboard/tournoi/add/add.component';

import { StadeComponent } from './dashboard/stade/stade.component';
import { AddStadeComponent } from './dashboard/stade/add/add.component';
import { EquipeComponent } from './dashboard/equipe/equipe.component';
import { AddEquipeComponent } from './dashboard/equipe/add/add.component';

@NgModule({
  declarations: [
    DashboardComponent,
    ReservationListComponent,
    AddReservationComponent,
    TournoiComponent,
    AddTournoiComponent,
    StadeComponent,
    AddStadeComponent,
    EquipeComponent,
    AddEquipeComponent,



  ],
  imports: [
    CommonModule,
    BackOfficeRoutingModule,
    HttpClientModule,
    FormsModule
  ]
})
export class BackOfficeModule { }
