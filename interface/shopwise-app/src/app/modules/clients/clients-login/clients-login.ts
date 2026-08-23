import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Client } from '../clients.model';
import { ClientsService } from '../clients.service';

type LoginStep = 'email' | 'set-password' | 'enter-password';

@Component({
  selector: 'app-clients-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './clients-login.html',
  styleUrl: './clients-login.css'
})
export class ClientsLogin {
  private readonly fb = inject(FormBuilder);
  private readonly clientService = inject(ClientsService);
  private readonly router = inject(Router);

  readonly step = signal<LoginStep>('email');
  readonly currentClient = signal<Client | null>(null);

  readonly loading = signal<boolean>(false);
  readonly error = signal<string | null>(null);
  readonly success = signal<string | null>(null);

  // Formulaire Étape 1 : Email
  readonly emailForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]]
  });

  // Formulaire Étape 2a : Définition du mot de passe
  readonly setPasswordForm = this.fb.nonNullable.group({
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  // Formulaire Étape 2b : Connexion avec mot de passe
  readonly loginPasswordForm = this.fb.nonNullable.group({
    password: ['', [Validators.required]]
  });

  // 1. Validation de l'email
  onCheckEmail(): void {
    if (this.emailForm.invalid) {
      this.emailForm.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    const email = this.emailForm.getRawValue().email.trim();

    this.clientService.getClientByEmail(email).subscribe({
      next: (client) => {
        this.currentClient.set(client);
        this.loading.set(false);

        // Si hashPassword est null -> définir un mot de passe, sinon -> connexion
        if (!client.hashPassword) {
          this.step.set('set-password');
        } else {
          this.step.set('enter-password');
        }
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.status === 404 ? 'Aucun compte associé à cet email.' : 'Erreur de communication avec le serveur.');
        console.error(err);
      }
    });
  }

  // 2a. Définition du mot de passe initial
  onSetPassword(): void {
    if (this.setPasswordForm.invalid) {
      this.setPasswordForm.markAllAsTouched();
      return;
    }

    const client = this.currentClient();
    if (!client || !client.id) return;

    this.loading.set(true);
    this.error.set(null);

    const rawPassword = this.setPasswordForm.getRawValue().password;

    this.clientService.putClient(client.id, {
      ...client,
      hashPassword: rawPassword
    }).subscribe({
      next: (updatedClient) => {
        this.loading.set(false);
        this.currentClient.set(updatedClient);
        this.success.set('Mot de passe défini avec succès ! Connexion en cours...');
        setTimeout(() => this.router.navigate(['/clients']), 1200);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set('Impossible d’enregistrer le mot de passe.');
        console.error(err);
      }
    });
  }

  // 2b. Connexion par mot de passe
  onLogin(): void {
    if (this.loginPasswordForm.invalid) {
      this.loginPasswordForm.markAllAsTouched();
      return;
    }

    const client = this.currentClient();
    if (!client || !client.id) return;

    this.loading.set(true);
    this.error.set(null);

    const rawPassword = this.loginPasswordForm.getRawValue().password;

    this.clientService.loginClient({
      id: client.id,
      email: client.email,
      hashPassword: rawPassword
    }).subscribe({
      next: () => {
        this.loading.set(false);
        this.success.set('Authentification réussie ! Redirection...');
        setTimeout(() => this.router.navigate([`/clients/${client.id}`]), 1000);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.status === 401 ? 'Mot de passe incorrect.' : 'Erreur de connexion.');
        console.error(err);
      }
    });
  }

  resetFlow(): void {
    this.step.set('email');
    this.currentClient.set(null);
    this.error.set(null);
    this.success.set(null);
    this.emailForm.reset();
    this.setPasswordForm.reset();
    this.loginPasswordForm.reset();
  }
}
