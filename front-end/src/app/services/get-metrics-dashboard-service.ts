import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface GetMetricsDashboardServiceResponse {
  spent: number
  deposit: number
  investment: number
  sale: number
}

@Injectable({
  providedIn: 'root'
})
export class GetMetricsDashboardService {
  constructor(
    private httpClient: HttpClient
  ) { }

  public execute(): Observable<GetMetricsDashboardServiceResponse> {
    return this.httpClient.get<GetMetricsDashboardServiceResponse>("api/transactions/metrics",
      {
        headers: {
          "Authorization": `${localStorage.getItem("Authorization")}`
        }
      }
    )
  }

}
