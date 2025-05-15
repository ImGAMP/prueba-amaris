import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },

  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule)
  },
  {
    path: 'usuarios',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/usuarios/usuarios.module').then((m) => m.UsuariosModule)
  },
  {
    path: 'empleados',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/empleados/empleados.module').then((m) => m.EmpleadosModule)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
