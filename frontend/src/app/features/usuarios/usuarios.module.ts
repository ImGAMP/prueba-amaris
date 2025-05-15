import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { USUARIOS_ROUTES } from './usuarios.routes';
import { FormularioComponent } from './formulario/formulario.component';
import { ListadoComponent } from './listado/listado.component';

@NgModule({
  imports: [
    RouterModule.forChild(USUARIOS_ROUTES),
    FormularioComponent,
    ListadoComponent
  ]
})
export class UsuariosModule {}
