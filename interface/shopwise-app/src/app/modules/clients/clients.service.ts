import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from './clients.model';
import { RendezVous } from '../commercants/commercants.model';

@Injectable({
  providedIn: 'root'
})
export class ClientsService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8000';

  getClientByEmail(email: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/clients/email/${email}`);
  }

  loginClient(client: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}/clients/login`, client);
  }

  getClientAppointments(id: string): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(`${this.apiUrl}/rendez-vous/clients/${id}`);
  }

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/clients`);
  }

  createClient(client: Omit<Client, 'id'>): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}/clients`, client);
  }

  putClient(id: number, client: Partial<Client>): Observable<Client> {
    return this.http.put<Client>(`${this.apiUrl}/clients/${id}`, client);
  }
}
