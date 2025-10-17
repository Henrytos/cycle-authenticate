import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';

interface CreateAccountServiceRequest {
  username: string,
  email: string,
  password: string,
  dateOfBirth: string,
}

interface CreateAccountServiceRespons {
  username: string
  email: string
  userRole: string
}


@Injectable({
  providedIn: 'root'
})
export class CreateAccountService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public execute({ username, email, password, dateOfBirth }: CreateAccountServiceRequest) {

    return this.httpClient.post<CreateAccountServiceRespons>("api/users", {
      username, email, password, dateOfBirth, userRole: "ROLE_USER"
    })
  }
}
