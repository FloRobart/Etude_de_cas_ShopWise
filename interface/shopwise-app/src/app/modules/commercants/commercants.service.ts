// src/app/core/services/product.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Commercant, RendezVous } from './commercants.model';

@Injectable({
  providedIn: 'root'
})
export class CommercantsService {
  private readonly http = inject(HttpClient);
  // URL de base de l'API Spring Boot
  private readonly apiUrl = 'http://localhost:8000';

  getCommercants(): Observable<Commercant[]> {
    return this.http.get<Commercant[]>(`${this.apiUrl}/commercants`);
  }

  getCommercantById(id: number): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(`${this.apiUrl}/rendez-vous/commercants/${id}`);
  }


  createRendezVous(rdv: Omit<RendezVous, 'id'>): Observable<RendezVous> {
    return this.http.post<RendezVous>(`${this.apiUrl}/rendez-vous`, rdv);
  }

  updateRendezVous(id: number, rdv: RendezVous): Observable<RendezVous> {
    return this.http.put<RendezVous>(`${this.apiUrl}/rendez-vous/${id}`, rdv);
  }
}
