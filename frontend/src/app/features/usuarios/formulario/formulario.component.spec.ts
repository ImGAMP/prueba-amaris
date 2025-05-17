import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormularioComponent } from './formulario.component';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { UsuarioService } from '../../../core/services/usuario.service';
import { Usuario } from '../../../core/models/usuario.model';

describe('FormularioComponent', () => {
  let component: FormularioComponent;
  let fixture: ComponentFixture<FormularioComponent>;
  let usuarioServiceSpy: jasmine.SpyObj<UsuarioService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const routeMock = {
      snapshot: {
        paramMap: {
          get: (key: string) => null // por defecto sin id
        }
      }
    };

    await TestBed.configureTestingModule({
      imports: [FormularioComponent],
      providers: [
        { provide: ActivatedRoute, useValue: routeMock },
        { provide: UsuarioService, useValue: jasmine.createSpyObj('UsuarioService', ['getById', 'create', 'update']) },
        { provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FormularioComponent);
    component = fixture.componentInstance;
    usuarioServiceSpy = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not enter edit mode if no ID in route', () => {
    expect(component.isEdit).toBeFalse();
  });

  it('should enter edit mode and load user when ID is present', fakeAsync(() => {
    const route = TestBed.inject(ActivatedRoute);
    spyOn(route.snapshot.paramMap, 'get').and.returnValue('1');

    const mockUsuario: Usuario = { id: 1, username: 'test', enabled: true };
    usuarioServiceSpy.getById.and.returnValue(of(mockUsuario));

    component.ngOnInit();
    tick();

    expect(component.isEdit).toBeTrue();
    expect(usuarioServiceSpy.getById).toHaveBeenCalledWith(1);
    expect(component.usuario).toEqual(mockUsuario);
  }));

  it('should redirect to /usuarios if getById fails', fakeAsync(() => {
    const route = TestBed.inject(ActivatedRoute);
    spyOn(route.snapshot.paramMap, 'get').and.returnValue('1');
    usuarioServiceSpy.getById.and.returnValue(throwError(() => new Error('Not found')));

    component.ngOnInit();
    tick();

    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios']);
  }));

  it('should call update when isEdit is true', () => {
    component.isEdit = true;
    component.usuario = { id: 2, username: 'edituser', enabled: true };
    usuarioServiceSpy.update.and.returnValue(of(component.usuario));

    component.guardar();

    expect(usuarioServiceSpy.update).toHaveBeenCalledWith(2, component.usuario);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios']);
  });

  it('should call create when isEdit is false', () => {
    component.isEdit = false;
    component.usuario = { id: 0, username: 'newuser', enabled: true };
    usuarioServiceSpy.create.and.returnValue(of({ ...component.usuario, id: 1 }));

    component.guardar();

    expect(usuarioServiceSpy.create).toHaveBeenCalledWith(component.usuario);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios']);
  });

  it('should navigate away on cancelar()', () => {
    component.cancelar();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/usuarios']);
  });
});
