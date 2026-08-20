// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  // Redirection de la racine vers la liste des produits
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home'
  },
  // Lazy loading des composants standalone
  {
    path: 'home',
    loadComponent: () => import('./modules/home/home').then(m => m.Home)
  },
  {
    path: 'commercants',
    loadComponent: () => import('./modules/commercants/commercants-list/commercants-list').then(m => m.CommercantsList)
  },
  {
    path: 'commercants/:id',
    loadComponent: () => import('./modules/commercants/commercants-details/commercants-details').then(m => m.CommercantsDetails)
  },
  {
    path: 'clients/login',
    loadComponent: () =>
      import('./modules/clients/clients-login/clients-login').then(
        (m) => m.ClientsLogin
      )
  },
  {
    path: 'clients/:id',
    loadComponent: () => import('./modules/clients/clients-details/clients-details').then(m => m.ClientsDetails)
  },
  // Redirection ou fallback 404 (Wildcard)
  {
    path: '**',
    redirectTo: 'home'
  }
];
