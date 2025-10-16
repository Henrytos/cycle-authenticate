import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface CreateNewTransactionServiceRequest {
  title: string
  value: number
  typeTransaction: string
  methodPayment: string
  dateOfPayment: string
}

interface CreateNewTransactionServiceResponse extends CreateNewTransactionServiceRequest {
  id: string
  senderId: string
}

@Injectable({
  providedIn: 'root'
})
export class CreateNewTransactionService {
  constructor(
    private httpClient: HttpClient
  ) { }

  public execute(data: CreateNewTransactionServiceRequest): Observable<CreateNewTransactionServiceResponse> {
    console.log(data)
    return this.httpClient.post<CreateNewTransactionServiceResponse>("api/transactions", data)
  }

}
