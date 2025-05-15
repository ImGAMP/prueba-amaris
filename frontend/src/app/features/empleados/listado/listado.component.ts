import { Component, OnInit } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../core/services/empleado.service';

@Component({
  standalone: true,
  selector: 'app-listado-empleados',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoEmpleadoComponent implements OnInit {
  empleados: any[] = [];
  loading = false;
  error = '';
  busquedaId: number | null = null;

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  cargarEmpleados(): void {
    this.loading = true;
    this.error = '';
    this.busquedaId = null;

    this.empleadoService.getAll().subscribe({
      next: (data) => {
        this.empleados = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los empleados';
        this.empleados = [];
        this.loading = false;
      }
    });
  }

  buscarPorId(): void {
    if (!this.busquedaId || this.busquedaId <= 0) {
      this.cargarEmpleados();
      return;
    }

    this.loading = true;
    this.error = '';
    this.empleados = [];

    this.empleadoService.getById(this.busquedaId).subscribe({
      next: (empleado) => {
        if (empleado && empleado.id) {
          this.empleados = [empleado];
        } else {
          this.error = 'Empleado no encontrado';
        }
        this.loading = false;
      },
      error: () => {
        this.error = 'Empleado no encontrado';
        this.loading = false;
      }
    });
  }

  nuevo(): void {
    this.router.navigate(['/empleados/formulario']);
  }

  editar(id: number): void {
    this.router.navigate(['/empleados/formulario', id]);
  }

  eliminar(id: number): void {
    if (confirm('¿Está seguro que desea eliminar este empleado?')) {
      this.loading = true;
      this.empleadoService.delete(id).subscribe({
        next: () => {
          this.cargarEmpleados();
        },
        error: () => {
          this.error = 'No se pudo eliminar el empleado.';
          this.loading = false;
        }
      });
    }
  }
}
