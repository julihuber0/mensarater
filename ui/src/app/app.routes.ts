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
    path: '**',
    loadComponent: () => import('./pages/overview/overview.component').then(c => c.OverviewComponent)
  }
];
