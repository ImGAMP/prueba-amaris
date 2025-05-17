import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListadoComponent } from './listado.component';
import { UsuarioService } from '../../../core/services/usuario.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('ListadoComponent', () => {
  let component: ListadoComponent;
  let fixture: ComponentFixture<ListadoComponent>;
  let usuarioServiceSpy: jasmine.SpyObj<UsuarioService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const usuarioMock = jasmine.createSpyObj('UsuarioService', ['getAll', 'delete']);
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ListadoComponent],
      providers: [
        { provide: UsuarioService, useValue: usuarioMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListadoComponent);
    component = fixture.componentInstance;
    usuarioServiceSpy = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load usuarios on init', () => {
    const mockUsuarios = [{ id: 1, username: 'admin', enabled: true }];
    usuarioServiceSpy.getAll.and.returnValue(of(mockUsuarios));

    component.ngOnInit();
    expect(usuarioServiceSpy.getAll).toHaveBeenCalled();
    expect(component.usuarios).toEqual(mockUsuarios);
    expect(component.loading).toBeFalse();
  });

  it('should call delete and reload list', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    usuarioServiceSpy.delete.and.returnValue(of(undefined));
    usuarioServiceSpy.getAll.and.returnValue(of([]));

    component.eliminar(1);

    expect(usuarioServiceSpy.delete).toHaveBeenCalledWith(1);
    expect(usuarioServiceSpy.getAll).toHaveBeenCalled();
  });

  it('should navigate to edit on editar()', () => {
    component.editar(5);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios/formulario', 5]);
  });

  it('should navigate to nuevo formulario', () => {
    component.nuevo();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios/formulario']);
  });
});
