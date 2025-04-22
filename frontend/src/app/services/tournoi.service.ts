import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TournoiService {
  private apiUrl = 'http://localhost:8087/tournois';

  constructor(private http: HttpClient) {}

  getAllTournois(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  deleteTournoi(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Ajoute create / update si nécessaire
}
