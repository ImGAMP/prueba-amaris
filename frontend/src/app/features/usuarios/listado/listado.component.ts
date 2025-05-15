import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Usuario } from '../../../core/models/usuario.model';
import { UsuarioService } from '../../../core/services/usuario.service';

@Component({
  standalone: true,
  selector: 'app-listado-usuarios',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoComponent implements OnInit {
  usuarios: Usuario[] = [];
  loading = false;

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.loading = true;
    this.usuarioService.getAll().subscribe({
      next: data => {
        this.usuarios = data;
        this.loading = false;
      },
      error: err => {
        console.error('Error al cargar usuarios', err);
        this.loading = false;
      }
    });
  }

  editar(id: number): void {
    this.router.navigate(['/usuarios/formulario', id]);
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.usuarioService.delete(id).subscribe(() => this.cargarUsuarios());
    }
  }

  nuevo(): void {
    this.router.navigate(['/usuarios/formulario']);
  }
}
