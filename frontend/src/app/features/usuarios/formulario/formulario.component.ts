import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../core/services/usuario.service';
import { Usuario } from '../../../core/models/usuario.model';

@Component({
  standalone: true,
  selector: 'app-formulario-usuario',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './formulario.component.html',
  styleUrls: ['./formulario.component.scss']
})
export class FormularioComponent implements OnInit {
  usuario: Usuario = { id: 0, username: '', enabled: true };
  isEdit = false;
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loading = true;
      this.usuarioService.getById(+id).subscribe({
        next: (res) => {
          this.usuario = res;
          this.loading = false;
        },
        error: () => {
          this.router.navigate(['/usuarios']);
        }
      });
    }
  }

  guardar(): void {
    if (this.isEdit) {
      this.usuarioService.update(this.usuario.id, this.usuario).subscribe(() => {
        this.router.navigate(['/usuarios']);
      });
    } else {
      this.usuarioService.create(this.usuario).subscribe(() => {
        this.router.navigate(['/usuarios']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/usuarios']);
  }
}
