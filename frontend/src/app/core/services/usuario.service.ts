import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Usuario } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private baseUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Usuario[]> {
    return this.http.get<any>(this.baseUrl).pipe(
      map(res => res.data.map((item: any) => this.toUsuario(item)))
    );
  }

  getById(id: number): Observable<Usuario> {
    return this.http.get<any>(`${this.baseUrl}/${id}`).pipe(
      map(res => this.toUsuario(res.data))
    );
  }

  create(usuario: Usuario): Observable<Usuario> {
    return this.http.post<any>(this.baseUrl, usuario).pipe(
      map(res => this.toUsuario(res.data))
    );
  }

  update(id: number, usuario: Usuario): Observable<Usuario> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, usuario).pipe(
      map(res => this.toUsuario(res.data))
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  private toUsuario(data: any): Usuario {
    return {
      id: data.id,
      username: data.attributes.username,
      enabled: data.attributes.enabled
    };
  }
}
