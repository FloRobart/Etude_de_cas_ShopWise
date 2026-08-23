import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ClientsService } from '../clients.service';

export interface RendezVous {
  id: number;
  clientId: number;
  commercantId: number;
  appointmentDate: string;
  serviceType: string | null;
  status: string;
  fidelityPoints: number;
}

@Component({
  selector: 'app-client-details',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './clients-details.html',
  styleUrl: './clients-details.css'
})
export class ClientsDetails {
  private readonly clientService = inject(ClientsService);

  readonly id = input.required<string>();

  readonly appointments = signal<RendezVous[]>([]);
  readonly loading = signal<boolean>(true);
  readonly error = signal<string | null>(null);

  // Calcul automatique réactif du total des points de fidélité
  readonly totalFidelityPoints = computed(() => {
    return this.appointments().reduce((total, rdv) => {
      const points = typeof rdv.fidelityPoints === 'number' ? rdv.fidelityPoints : parseInt(rdv.fidelityPoints || '0', 10);
      return total + (isNaN(points) ? 0 : points);
    }, 0);
  });

  constructor() {
    effect(() => {
      const id = this.id();
      if (id) {
        this.fetchAppointments(id);
      }
    });
  }

  private fetchAppointments(id: string): void {
    this.loading.set(true);
    this.error.set(null);

    this.clientService.getClientAppointments(id).subscribe({
      next: (data) => {
        this.appointments.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.error.set('Impossible de charger les rendez-vous pour ce client.');
        this.loading.set(false);
      }
    });
  }
}
