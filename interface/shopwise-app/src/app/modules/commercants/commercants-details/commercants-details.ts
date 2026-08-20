import { Component, effect, inject, input, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe, UpperCasePipe } from '@angular/common';
import { CommercantsService } from '../commercants.service';
import { RendezVous } from '../commercants.model';
import { Client } from '../../clients/clients.model';
import { ClientsService } from '../../clients/clients.service';

@Component({
  selector: 'app-commercants-details',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, DatePipe, UpperCasePipe],
  templateUrl: './commercants-details.html',
  styleUrl: './commercants-details.css'
})
export class CommercantsDetails {
  private readonly fb = inject(FormBuilder);
  private readonly commercantService = inject(CommercantsService);
  private readonly clientService = inject(ClientsService);

  readonly id = input.required<string>();

  // Rendez-vous
  readonly data = signal<RendezVous[]>([]);
  readonly loading = signal<boolean>(true);
  readonly error = signal<string | null>(null);
  readonly updatingRdvIds = signal<Set<number>>(new Set());

  // Formulaire Rendez-vous
  readonly isSubmittingRdv = signal<boolean>(false);
  readonly rdvSuccess = signal<string | null>(null);
  readonly rdvError = signal<string | null>(null);

  readonly rdvForm = this.fb.nonNullable.group({
    clientId: [null as number | null, [Validators.required]],
    appointmentDate: ['', [Validators.required]],
    serviceType: ['Consultation', [Validators.required]],
    fidelityPoints: [10, [Validators.min(0)]],
    status: ['scheduled', [Validators.required]]
  });

  // Clients
  readonly clients = signal<Client[]>([]);
  readonly clientsLoading = signal<boolean>(true);
  readonly clientsError = signal<string | null>(null);
  readonly selectedClient = signal<Client | null>(null);

  // Formulaire Client
  readonly isSubmittingClient = signal<boolean>(false);
  readonly clientSuccess = signal<string | null>(null);
  readonly clientError = signal<string | null>(null);

  readonly clientForm = this.fb.nonNullable.group({
    nom: ['', [Validators.required, Validators.minLength(2)]],
    prenom: [''],
    email: ['', [Validators.required, Validators.email]],
    phone: ['']
  });

  private readonly statusFlow: Record<string, string> = {
    scheduled: 'completed',
    completed: 'canceled',
    canceled: 'scheduled'
  };

  constructor() {
    this.fetchClients();

    effect(() => {
      const commercantId = this.id();
      if (commercantId) {
        this.fetchCommercant(commercantId);
      }
    });
  }

  private fetchClients(): void {
    this.clientsLoading.set(true);
    this.clientsError.set(null);

    this.clientService.getClients().subscribe({
      next: (data) => {
        this.clients.set(data);
        this.clientsLoading.set(false);
      },
      error: (err) => {
        this.clientsError.set('Impossible de récupérer les clients.');
        this.clientsLoading.set(false);
        console.error(err);
      }
    });
  }

  private fetchCommercant(id: string): void {
    this.loading.set(true);
    this.error.set(null);

    const parsedId = parseInt(id, 10);
    if (isNaN(parsedId)) {
      this.error.set('ID de commerçant invalide.');
      this.loading.set(false);
      return;
    }

    this.commercantService.getCommercantById(parsedId).subscribe({
      next: (data) => {
        this.data.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Impossible de récupérer les informations du commerçant.');
        this.loading.set(false);
        console.error(err);
      }
    });
  }

  onSubmitRdv(): void {
    if (this.rdvForm.invalid) {
      this.rdvForm.markAllAsTouched();
      return;
    }

    const commercantId = parseInt(this.id(), 10);
    if (isNaN(commercantId)) return;

    this.isSubmittingRdv.set(true);
    this.rdvSuccess.set(null);
    this.rdvError.set(null);

    const formValues = this.rdvForm.getRawValue();

    // Format ISO requis pour le Timestamp Spring Boot
    const formattedDate = new Date(formValues.appointmentDate).toISOString();

    const newRdv: Omit<RendezVous, 'id'> = {
      clientId: Number(formValues.clientId),
      commercantId: commercantId,
      appointmentDate: formattedDate,
      serviceType: formValues.serviceType,
      status: formValues.status,
      fidelityPoints: 0
    };

    this.commercantService.createRendezVous(newRdv).subscribe({
      next: (created) => {
        this.rdvSuccess.set('Rendez-vous planifié avec succès !');
        this.data.update((items) => [created, ...items]);
        this.rdvForm.reset({
          clientId: null,
          appointmentDate: '',
          serviceType: 'Consultation',
          status: 'scheduled'
        });
        this.isSubmittingRdv.set(false);
      },
      error: (err) => {
        this.rdvError.set('Erreur lors de la création du rendez-vous.');
        this.isSubmittingRdv.set(false);
        console.error(err);
      }
    });
  }

  cycleStatus(rdv: RendezVous): void {
    if (this.updatingRdvIds().has(rdv.id)) return;

    const current = (rdv.status || 'scheduled').toLowerCase();
    const nextStatus = this.statusFlow[current] || 'scheduled';

    const updatedPayload: RendezVous = {
      ...rdv,
      fidelityPoints: 0,
      status: nextStatus
    };

    this.updatingRdvIds.update((ids) => new Set(ids).add(rdv.id));

    this.commercantService.updateRendezVous(rdv.id, updatedPayload).subscribe({
      next: (savedRdv) => {
        this.data.update((items) =>
          items.map((item) => (item.id === savedRdv.id ? savedRdv : item))
        );
        this.removeUpdatingId(rdv.id);
      },
      error: (err) => {
        console.error('Erreur mise à jour statut :', err);
        this.removeUpdatingId(rdv.id);
      }
    });
  }

  private removeUpdatingId(id: number): void {
    this.updatingRdvIds.update((ids) => {
      const next = new Set(ids);
      next.delete(id);
      return next;
    });
  }

  selectClient(client: Client): void {
    this.selectedClient.set(client);
    this.clientSuccess.set(null);
    this.clientError.set(null);

    // Remplir aussi le sélecteur du formulaire RDV par commodité
    if (client.id) {
      this.rdvForm.patchValue({ clientId: client.id });
    }

    this.clientForm.patchValue({
      nom: client.nom,
      prenom: client.prenom ?? '',
      email: client.email,
      phone: client.phone ?? ''
    });
  }

  cancelEdit(): void {
    this.selectedClient.set(null);
    this.clientForm.reset();
    this.clientSuccess.set(null);
    this.clientError.set(null);
  }

  onSubmitClient(): void {
    if (this.clientForm.invalid) {
      this.clientForm.markAllAsTouched();
      return;
    }

    this.isSubmittingClient.set(true);
    this.clientSuccess.set(null);
    this.clientError.set(null);

    const formValues = this.clientForm.getRawValue();
    const current = this.selectedClient();

    const clientPayload: Client = {
      nom: formValues.nom,
      prenom: formValues.prenom.trim() ? formValues.prenom : null,
      email: formValues.email,
      phone: formValues.phone.trim() ? formValues.phone : null
    };

    if (current && current.id) {
      this.clientService.putClient(current.id, clientPayload).subscribe({
        next: (updated) => {
          this.clientSuccess.set(`Client ${updated.nom} mis à jour avec succès !`);
          this.clients.update((list) =>
            list.map((c) => (c.id === updated.id ? updated : c))
          );
          this.cancelEdit();
          this.isSubmittingClient.set(false);
        },
        error: (err) => {
          this.clientError.set('Erreur lors de la mise à jour du client.');
          this.isSubmittingClient.set(false);
          console.error(err);
        }
      });
    } else {
      this.clientService.createClient(clientPayload).subscribe({
        next: (created) => {
          this.clientSuccess.set(`Client ${created.nom} créé avec succès !`);
          this.clients.update((list) => [created, ...list]);
          if (created.id) {
            this.rdvForm.patchValue({ clientId: created.id });
          }
          this.clientForm.reset();
          this.isSubmittingClient.set(false);
        },
        error: (err) => {
          this.clientError.set("Erreur lors de l'enregistrement du client.");
          this.isSubmittingClient.set(false);
          console.error(err);
        }
      });
    }
  }
}
