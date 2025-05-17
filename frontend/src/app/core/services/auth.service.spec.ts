import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [{ provide: Router, useValue: routerMock }]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and store token', () => {
    const dummyToken = 'abc.def.ghi';
    const credentials = { username: 'admin', password: '1234' };

    service.login(credentials).subscribe(res => {
      expect(localStorage.getItem('jwt_token')).toBe(dummyToken);
    });

    const req = httpMock.expectOne(`${service['baseUrl']}/login`);
    expect(req.request.method).toBe('POST');
    req.flush({ token: dummyToken });
  });

  it('should remove token on logout and navigate to login', () => {
    localStorage.setItem('jwt_token', 'dummy');
    service.logout();
    expect(localStorage.getItem('jwt_token')).toBeNull();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/auth/login']);
  });

  it('should return null if token does not exist', () => {
    expect(service.getToken()).toBeNull();
  });

  it('should return true if token exists', () => {
    localStorage.setItem('jwt_token', 'exists');
    expect(service.isAuthenticated()).toBeTrue();
  });

  it('should return false if token is missing', () => {
    expect(service.isAuthenticated()).toBeFalse();
  });
});
