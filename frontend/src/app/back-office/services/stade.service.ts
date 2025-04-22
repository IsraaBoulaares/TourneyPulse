import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stade } from '../models/stade.model';

@Injectable({
  providedIn: 'root'
})
export class StadeService {
  private apiUrl = 'http://localhost:8082/stades'; // Remplacez par l'URL de votre backend

  constructor(private http: HttpClient) {}

  // GET: /stades - Récupère tous les stades
  getAllStades(): Observable<Stade[]> {
    return this.http.get<Stade[]>(this.apiUrl);
  }

  // GET: /stades/{id} - Récupère un stade par ID
  getStadeById(id: number): Observable<Stade> {
    return this.http.get<Stade>(`${this.apiUrl}/${id}`);
  }

  // POST: /stades - Crée un nouveau stade
  createStade(stade: Stade): Observable<Stade> {
    return this.http.post<Stade>(this.apiUrl, stade);
  }

  // PUT: /stades/{id} - Met à jour un stade existant
  updateStade(id: number, stade: Stade): Observable<Stade> {
    return this.http.put<Stade>(`${this.apiUrl}/${id}`, stade);
  }

  // DELETE: /stades/{id} - Supprime un stade
  deleteStade(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // GET: /stades/matches - Récupère les matchs (FootballData)
  getMatches(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/matches`);
  }

  // GET: /stades/competitions - Récupère les compétitions (FootballData)
  getCompetitions(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/competitions`);
  }

  // GET: /stades/areas - Récupère les zones (FootballData)
  getAreas(): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/areas`);
  }

  // GET: /stades/export/excel - Exporte la liste des stades en fichier Excel
  exportToExcel(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/excel`, {
      responseType: 'blob'
    });
  }
}
