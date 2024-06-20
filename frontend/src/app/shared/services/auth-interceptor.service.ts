import {
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const jwtToken = localStorage.getItem('jwtToken');
    // console.log('authToken', jwtToken);
    if (!jwtToken) {
      return next.handle(req);
    }

    const authRequest = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + jwtToken),
    });
    return next.handle(authRequest);
  }
}
