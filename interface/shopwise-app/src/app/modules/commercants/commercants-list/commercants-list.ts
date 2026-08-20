import { Component, OnInit, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CurrencyPipe } from '@angular/common';
import { Commercant } from '../commercants.model';
import { CommercantsService } from '../commercants.service';

@Component({
  selector: 'app-commercants-list',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './commercants-list.html',
  styleUrl: './commercants-list.css',
})
export class CommercantsList implements OnInit {
  private readonly productService = inject(CommercantsService);
  private readonly router = inject(Router);

  readonly data = signal<Commercant[]>([]);
  readonly loading = signal<boolean>(true);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.productService.getCommercants().subscribe({
      next: (data) => {
        console.debug('Commerçants récupérés avec succès :', data);
        this.data.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.debug('Erreur lors de la récupération des commerçants :', err);
        this.error.set('Impossible de contacter l’API Spring Boot.');
        this.loading.set(false);
      }
    });
  }

  goToDetail(id: number): void {
    // Redirection programmatique via le Router
    this.router.navigate(['/commercants', id]);
  }
}
