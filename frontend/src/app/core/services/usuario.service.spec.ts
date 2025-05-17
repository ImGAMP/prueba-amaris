import { TestBed } from '@angular/core/testing';
import { UsuarioService } from './usuario.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Usuario } from '../models/usuario.model';

describe('UsuarioService', () => {
  let service: UsuarioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(UsuarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all usuarios', () => {
    const mockResponse = {
      data: [
        {
          id: 1,
          attributes: {
            username: 'admin',
            enabled: true
          }
        }
      ]
    };

    service.getAll().subscribe(usuarios => {
      expect(usuarios.length).toBe(1);
      expect(usuarios[0].username).toBe('admin');
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should get usuario by id', () => {
    const mockResponse = {
      data: {
        id: 1,
        attributes: {
          username: 'admin',
          enabled: true
        }
      }
    };

    service.getById(1).subscribe(usuario => {
      expect(usuario.id).toBe(1);
      expect(usuario.username).toBe('admin');
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should create usuario', () => {
    const newUser: Usuario = { id: 0, username: 'nuevo', enabled: true };
    const mockResponse = {
      data: {
        id: 2,
        attributes: {
          username: 'nuevo',
          enabled: true
        }
      }
    };

    service.create(newUser).subscribe(usuario => {
      expect(usuario.id).toBe(2);
      expect(usuario.username).toBe('nuevo');
    });

    const req = httpMock.expectOne(`${service['baseUrl']}`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should update usuario', () => {
    const updatedUser: Usuario = { id: 1, username: 'modificado', enabled: false };
    const mockResponse = {
      data: {
        id: 1,
        attributes: {
          username: 'modificado',
          enabled: false
        }
      }
    };

    service.update(1, updatedUser).subscribe(usuario => {
      expect(usuario.username).toBe('modificado');
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/1`);
    expect(req.request.method).toBe('PUT');
    req.flush(mockResponse);
  });

  it('should delete usuario', () => {
    service.delete(1).subscribe(result => {
      expect(result).toBeNull();
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null); 
  });
});
