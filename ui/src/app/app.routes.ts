import {Routes} from '@angular/router';
import {canRate} from "./guards/authentication.guard";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/overview/overview.component').then(c => c.OverviewComponent)
  },
  {
    path: 'rating',
    loadComponent: () => import('./pages/overview/overview.component').then(c => c.OverviewComponent),
    canActivate: [canRate],
    data: {
      role: 'mensarater-user'
    }
  },
  {
    path: 'imprint',
    loadComponent: () => import('./pages/imprint/imprint.component').then(c => c.ImprintComponent)
  },
  {
    path: 'privacy',
    loadComponent: () => import('./pages/privacy/privacy.component').then(c => c.PrivacyComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./pages/overview/overview.component').then(c => c.OverviewComponent)
  }
];
