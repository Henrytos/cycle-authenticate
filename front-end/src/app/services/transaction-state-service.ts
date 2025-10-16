import { Injectable, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { GetMetricsDashboardServiceResponse } from './get-metrics-dashboard-service';
import { TransactionI } from '../pages/dashboard/dashboard';
import { HttpClient } from '@angular/common/http';

type TransactionStateServiceResponse = TransactionI[]

@Injectable({
  providedIn: 'root'
})
export class TransactionStateService {
  private transactionMetricsSource = new Subject<GetMetricsDashboardServiceResponse>()
  private transactionRecentsSource = new Subject<TransactionI[]>()

  transactionMetricsUpdate$: Observable<GetMetricsDashboardServiceResponse> = this.transactionMetricsSource.asObservable();
  transactionRecentUpdate$: Observable<TransactionI[]> = this.transactionRecentsSource.asObservable();

  constructor(
    private httpClient: HttpClient
  ) {
    this.loadTransactions()

  }

  loadTransactions() {
    this.httpClient.get<TransactionStateServiceResponse>("api/transactions/recents", {
      headers: {
        "Authorization": `${localStorage.getItem("Authorization")}`
      }
    }).subscribe(res => {
      this.transactionRecentsSource.next(res);
    })
  }

  notifyTransactionsUpdated(metrics: GetMetricsDashboardServiceResponse): void {
    this.transactionMetricsSource.next(metrics)
  }

  notifyNewTransactionsUpdated(transactions: TransactionI[]) {
    this.transactionRecentsSource.next(transactions)
  }

}
