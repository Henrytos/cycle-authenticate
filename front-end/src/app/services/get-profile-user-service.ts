import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';

interface GetProfileUserServiceResponse {
  username: string
  email: string
  userRole: string
  dateOfBirth: string
}


@Injectable({
  providedIn: 'root'
})
export class GetProfileUserService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public execute(): Observable<GetProfileUserServiceResponse> {
    let username = localStorage.getItem("username")

    if (username != null) {
      return of({ username } as GetProfileUserServiceResponse)
    }

    return this.httpClient.get<GetProfileUserServiceResponse>("api/me", {
      headers: {
        "Authorization": `${localStorage.getItem("Authorization")}`
      }
    }).pipe(
      tap((res) => {
        localStorage.setItem("username", res.username)
      })
    )

  }

}
