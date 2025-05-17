import { TestBed } from '@angular/core/testing';
import { EmpleadoService } from './empleado.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';

describe('EmpleadoService', () => {
  let service: EmpleadoService;
  let httpMock: HttpTestingController;
  const baseUrl = `${environment.apiUrl}/empleados`;

  const mockEmpleadoJsonApi = {
    data: [
      {
        id: 1,
        attributes: {
          employee_name: 'Alice',
          employee_salary: 2000,
          employee_age: 28,
          profile_image: ''
        }
      }
    ]
  };

  const mockEmpleadoById = {
    data: {
      id: 1,
      attributes: {
        employee_name: 'Bob',
        employee_salary: 2500,
        employee_anual_salary: 30000,
        employee_age: 30,
        profile_image: 'img.png'
      }
    }
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(EmpleadoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all employees correctly', () => {
    service.getAll().subscribe((data) => {
      expect(data.length).toBe(1);
      expect(data[0].employee_name).toBe('Alice');
      expect(data[0].salary_anual).toBe(24000); // 2000 * 12
    });

    const req = httpMock.expectOne(baseUrl);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmpleadoJsonApi);
  });

  it('should fetch employee by ID', () => {
    service.getById(1).subscribe((data) => {
      expect(data.id).toBe(1);
      expect(data.employee_name).toBe('Bob');
      expect(data.salary_anual).toBe(30000);
    });

    const req = httpMock.expectOne(`${baseUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmpleadoById);
  });

  it('should handle delete employee', () => {
    service.delete(1).subscribe((res) => {
      expect(res).toBeTruthy();
    });

    const req = httpMock.expectOne(`${baseUrl}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush({ success: true });
  });

  it('should handle error in getAll', () => {
    service.getAll().subscribe({
      next: () => fail('should have failed'),
      error: (err) => expect(err).toBeTruthy()
    });

    const req = httpMock.expectOne(baseUrl);
    req.error(new ErrorEvent('Network error'));
  });

  it('should handle error in getById', () => {
    service.getById(99).subscribe({
      next: () => fail('should have failed'),
      error: (err) => expect(err).toBeTruthy()
    });

    const req = httpMock.expectOne(`${baseUrl}/99`);
    req.error(new ErrorEvent('Not found'));
  });

  it('should handle error in delete', () => {
    service.delete(1).subscribe({
      next: () => fail('should have failed'),
      error: (err) => expect(err).toBeTruthy()
    });

    const req = httpMock.expectOne(`${baseUrl}/1`);
    req.error(new ErrorEvent('Delete error'));
  });
});
