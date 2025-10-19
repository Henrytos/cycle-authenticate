import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs';

interface AuthenticateUserServiceRequest {
  email: string;
  password: string;
}

interface AuthenticateUserServiceResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthenticateUserService {
  constructor(private httpClient: HttpClient) {}

  execute({ email, password }: AuthenticateUserServiceRequest) {
    return this.httpClient
      .post<AuthenticateUserServiceResponse>('api/users/auth', { email, password })
      .pipe(
        tap((res) => {
          console.log(res.token);
        })
      );
  }

  setToken(token: string) {
    localStorage.setItem('Authorization', `Bearer ${token}`);
  }

  getToken() {
    return localStorage.getItem('Authorization');
  }

  isLoggedIn() {
    const token = localStorage.getItem('Authorization');

    if (token == '' || !token?.includes('Bearer')) {
      return false;
    }

    return true;
  }

  logout() {
    localStorage.removeItem('Authorization');
  }
}
