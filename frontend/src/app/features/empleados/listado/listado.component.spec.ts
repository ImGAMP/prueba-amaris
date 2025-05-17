import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ListadoEmpleadoComponent } from './listado.component';
import { EmpleadoService } from '../../../core/services/empleado.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ListadoEmpleadoComponent', () => {
  let component: ListadoEmpleadoComponent;
  let fixture: ComponentFixture<ListadoEmpleadoComponent>;
  let empleadoServiceSpy: jasmine.SpyObj<EmpleadoService>;
  let routerSpy: jasmine.SpyObj<Router>;

  const mockEmpleado = {
    id: 1,
    employee_name: 'John',
    employee_salary: 1000,
    employee_age: 30,
    salary_anual: 12000,
    profile_image: ''
  };

  beforeEach(async () => {
    const empleadoSpy = jasmine.createSpyObj('EmpleadoService', ['getAll', 'getById', 'delete']);
    const routeSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ListadoEmpleadoComponent, HttpClientTestingModule],
      providers: [
        { provide: EmpleadoService, useValue: empleadoSpy },
        { provide: Router, useValue: routeSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListadoEmpleadoComponent);
    component = fixture.componentInstance;
    empleadoServiceSpy = TestBed.inject(EmpleadoService) as jasmine.SpyObj<EmpleadoService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load all employees on init', () => {
    empleadoServiceSpy.getAll.and.returnValue(of([mockEmpleado]));
    component.ngOnInit();
    expect(empleadoServiceSpy.getAll).toHaveBeenCalled();
    expect(component.empleados.length).toBe(1);
  });

  it('should handle error on cargarEmpleados', () => {
    empleadoServiceSpy.getAll.and.returnValue(throwError(() => new Error()));
    component.cargarEmpleados();
    expect(component.error).toBe('No se pudieron cargar los empleados');
    expect(component.loading).toBeFalse();
  });

  it('should search by ID when busquedaId is set', () => {
    component.busquedaId = 1;
    empleadoServiceSpy.getById.and.returnValue(of(mockEmpleado));
    component.buscarPorId();
    expect(empleadoServiceSpy.getById).toHaveBeenCalledWith(1);
    expect(component.empleados.length).toBe(1);
  });

  it('should reload all if search ID is empty or invalid', () => {
    component.busquedaId = null;
    empleadoServiceSpy.getAll.and.returnValue(of([mockEmpleado]));
    component.buscarPorId();
    expect(empleadoServiceSpy.getAll).toHaveBeenCalled();
  });

  it('should show error if employee not found', () => {
    component.busquedaId = 99;
    empleadoServiceSpy.getById.and.returnValue(of(null));
    component.buscarPorId();
    expect(component.error).toBe('Empleado no encontrado');
  });

  it('should navigate to edit on editar()', () => {
    component.editar(1);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/empleados/formulario', 1]);
  });

  it('should navigate to create on nuevo()', () => {
    component.nuevo();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/empleados/formulario']);
  });

  it('should delete employee and reload', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    empleadoServiceSpy.delete.and.returnValue(of({}));
    empleadoServiceSpy.getAll.and.returnValue(of([]));
    component.eliminar(1);
    expect(empleadoServiceSpy.delete).toHaveBeenCalledWith(1);
    expect(empleadoServiceSpy.getAll).toHaveBeenCalled();
  });

  it('should handle error on delete', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    empleadoServiceSpy.delete.and.returnValue(throwError(() => new Error()));
    component.eliminar(1);
    expect(component.error).toBe('No se pudo eliminar el empleado.');
  });
});

