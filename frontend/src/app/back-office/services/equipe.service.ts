import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipe } from '../models/equipe.model';
import { Users } from '../models/users.model';
import { ChatRequest } from '../models/chat-request.model';

@Injectable({
  providedIn: 'root'
})
export class EquipeService {
  private apiUrl = 'http://localhost:8081/teams'; // Remplacez par l'URL de votre backend

  constructor(private http: HttpClient) {}

  // POST: /equipes - Crée une nouvelle équipe
  createequipe(equipe: Equipe): Observable<Equipe> {
    return this.http.post<Equipe>(this.apiUrl, equipe);
  }

  // GET: /equipes/{id} - Récupère une équipe par ID
  getequipeById(id: number): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/${id}`);
  }

  // GET: /equipes - Récupère toutes les équipes
  getAllequipes(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(this.apiUrl);
  }

  // PUT: /equipes/{id} - Met à jour une équipe existante
  updateequipe(id: number, equipe: Equipe): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}`, equipe);
  }

  // DELETE: /equipes/{id} - Supprime une équipe
  deleteequipe(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // POST: /equipes/{equipeId}/players/{playerId} - Ajoute un joueur à une équipe
  addPlayerToequipe(equipeId: number, playerId: number): Observable<Equipe> {
    return this.http.post<Equipe>(`${this.apiUrl}/${equipeId}/players/${playerId}`, {});
  }

  // DELETE: /equipes/{equipeId}/players/{playerId} - Supprime un joueur d'une équipe
  removePlayerFromequipe(equipeId: number, playerId: number): Observable<Equipe> {
    return this.http.delete<Equipe>(`${this.apiUrl}/${equipeId}/players/${playerId}`);
  }

  // GET: /equipes/{equipeId}/players - Récupère les joueurs d'une équipe
  getequipePlayers(equipeId: number): Observable<Users[]> {
    return this.http.get<Users[]>(`${this.apiUrl}/${equipeId}/players`);
  }

  // POST: /equipes/chat - Interagit avec le chatbot
  getChatResponse(chatRequest: ChatRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/chat`, chatRequest);
  }

  // POST: /equipes/user - Crée un nouvel utilisateur
  createUser(user: Users): Observable<Users> {
    return this.http.post<Users>(`${this.apiUrl}/user`, user);
  }

  // GET: /equipes/export/pdf - Exporte la liste des équipes en PDF
  exportToPDF(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/pdf`, {
      responseType: 'blob'
    });
  }

  // GET: /equipes/search?name={name} - Recherche une équipe par nom
  searchequipe(name: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/search`, {
      params: { name }
    });
  }
}
