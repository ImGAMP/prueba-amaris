import { Routes } from '@angular/router';
import { authGuard } from '../../core/guards/auth.guard';
import { ListadoEmpleadoComponent } from './listado/listado.component';

export const empleadosRoutes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: '', component: ListadoEmpleadoComponent }
    ]
  }
];
