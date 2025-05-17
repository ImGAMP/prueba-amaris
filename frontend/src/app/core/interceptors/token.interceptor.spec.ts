import { TestBed } from '@angular/core/testing';
import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { of } from 'rxjs';

import { TokenInterceptor } from './token.interceptor';
import { AuthService } from '../services/auth.service';

describe('TokenInterceptor', () => {
  let interceptor: TokenInterceptor;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let handlerSpy: jasmine.SpyObj<HttpHandler>;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['getToken']);
    handlerSpy = jasmine.createSpyObj('HttpHandler', ['handle']);

    TestBed.configureTestingModule({
      providers: [
        TokenInterceptor,
        { provide: AuthService, useValue: authServiceSpy }
      ]
    });

    interceptor = TestBed.inject(TokenInterceptor);
    handlerSpy.handle.and.returnValue(of({} as HttpEvent<any>));
  });

  it('should add Authorization header if token is present', () => {
    const token = 'fake-token';
    authServiceSpy.getToken.and.returnValue(token);

    const req = new HttpRequest('GET', '/api/test');
    interceptor.intercept(req, handlerSpy).subscribe();

    const interceptedReq = handlerSpy.handle.calls.mostRecent().args[0];
    expect(interceptedReq.headers.get('Authorization')).toBe(`Bearer ${token}`);
  });

  it('should not add Authorization header if token is null', () => {
    authServiceSpy.getToken.and.returnValue(null);

    const req = new HttpRequest('GET', '/api/test');
    interceptor.intercept(req, handlerSpy).subscribe();

    const interceptedReq = handlerSpy.handle.calls.mostRecent().args[0];
    expect(interceptedReq.headers.has('Authorization')).toBeFalse();
  });
});
