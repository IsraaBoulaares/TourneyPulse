import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tournoi } from '../../models/tournoi.model';

@Injectable({
  providedIn: 'root',
})
export class TournoiService {
  private apiUrl = 'http://localhost:8087/tournois'; // adapte selon ton backend

  constructor(private http: HttpClient) {}

  // GET: /tournois
  getAllTournois(): Observable<Tournoi[]> {
    return this.http.get<Tournoi[]>(`${this.apiUrl}`);
  }

  // GET: /tournois/{id}
  getTournoiById(id: number): Observable<Tournoi> {
    return this.http.get<Tournoi>(`${this.apiUrl}/${id}`);
  }

  // POST: /tournois
  addTournoi(tournoi: Tournoi): Observable<Tournoi> {
    return this.http.post<Tournoi>(`${this.apiUrl}`, tournoi);
  }

  // PUT: /tournois/{id}
  updateTournoi(id: number, tournoi: Tournoi): Observable<Tournoi> {
    return this.http.put<Tournoi>(`${this.apiUrl}/${id}`, tournoi);
  }

  // DELETE: /tournois/{id}
  deleteTournoi(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
