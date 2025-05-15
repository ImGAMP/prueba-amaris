import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { empleadosRoutes } from './empleados.routes';
import { ListadoEmpleadoComponent } from './listado/listado.component';

@NgModule({
  imports: [
    RouterModule.forChild(empleadosRoutes),
    ListadoEmpleadoComponent
  ]
})
export class EmpleadosModule {}
