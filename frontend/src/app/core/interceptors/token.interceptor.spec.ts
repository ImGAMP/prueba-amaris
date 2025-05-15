import { TestBed } from '@angular/core/testing';
import { HttpRequest, HttpHandler, HttpEvent, HTTP_INTERCEPTORS } from '@angular/common/http';
import { of } from 'rxjs';

import { TokenInterceptor } from './token.interceptor';
import { AuthService } from '../services/auth.service';

describe('TokenInterceptor', () => {
  let interceptor: TokenInterceptor;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let mockHandler: HttpHandler;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['getToken']);
    TestBed.configureTestingModule({
      providers: [
        TokenInterceptor,
        { provide: AuthService, useValue: authServiceSpy }
      ]
    });

    interceptor = TestBed.inject(TokenInterceptor);
    mockHandler = {
      handle: jasmine.createSpy('handle').and.returnValue(of({} as HttpEvent<any>)),
    };
  });

  it('should add Authorization header if token exists', () => {
    const token = 'fake-token';
    authServiceSpy.getToken.and.returnValue(token);

    const req = new HttpRequest('GET', '/api/data');
    interceptor.intercept(req, mockHandler).subscribe();

    const modifiedReq = (mockHandler.handle as jasmine.Spy).calls.mostRecent().args[0];
    expect(modifiedReq.headers.get('Authorization')).toBe(`Bearer ${token}`);
  });

  it('should pass through request if token does not exist', () => {
    authServiceSpy.getToken.and.returnValue(null);

    const req = new HttpRequest('GET', '/api/data');
    interceptor.intercept(req, mockHandler).subscribe();

    const modifiedReq = (mockHandler.handle as jasmine.Spy).calls.mostRecent().args[0];
    expect(modifiedReq.headers.has('Authorization')).toBeFalse();
  });
});