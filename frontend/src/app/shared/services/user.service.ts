import { HttpClient } from '@angular/common/http';
import { Injectable, effect, inject, signal } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Credentials, LoggedInUser, User } from '../interfaces/user';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

const API_URL = `${environment.apiURL}/user`;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  http: HttpClient = inject(HttpClient);
  router: Router = inject(Router);

  user = signal<LoggedInUser | null>(null);

  constructor() {
    const jwtToken = localStorage.getItem('jwtToken');
    const role = localStorage.getItem('role');
    if (jwtToken) {
      const decodedTokenSubject = jwtDecode(jwtToken).sub;
      this.user.set({        
        email: decodedTokenSubject,
        role: role
      });
    }

    effect(() => {
      if (this.user()) {
        console.log('Successful login', this.user().email, this.user().role);
      } else {
        console.log('No user Logged In');
      }
    });
  }

  registerUser(user: User) {
    return this.http.post(`${API_URL}/register`, user);
  }  

  loginUser(credentials: Credentials) {
    return this.http.post<{ jwtToken: string, role: string }>(`${API_URL}/login`, credentials);
  }

  logoutUser() {
    this.user.set(null);
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('role');
    this.router.navigate(['login']);
  }

  readAllUsers() {
    return this.http.get<User[]>(`${API_URL}/all`);
  }

  deleteUser(id: string) {
    return this.http.delete(`${API_URL}/delete/${id}`);
  }
}