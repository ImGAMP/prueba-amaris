import { Routes } from '@angular/router';
import { authGuard } from '../../core/guards/auth.guard';
import { ListadoComponent } from './listado/listado.component';
import { FormularioComponent } from './formulario/formulario.component';

export const USUARIOS_ROUTES: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: '', component: ListadoComponent },
      { path: 'nuevo', component: FormularioComponent }
    ]
  }
];
