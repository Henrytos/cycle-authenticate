import { Injectable, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { GetMetricsDashboardServiceResponse } from './get-metrics-dashboard-service';
import { Expenses, TransactionI } from '../pages/dashboard/dashboard';
import { HttpClient } from '@angular/common/http';

type TransactionStateServiceResponse = TransactionI[]

@Injectable({
  providedIn: 'root'
})
export class TransactionStateService {
  private transactionMetricsSource = new Subject<GetMetricsDashboardServiceResponse>()
  private transactionRecentsSource = new Subject<TransactionI[]>()
  private transactionThreeBiggestExpensesSource = new Subject<Expenses[]>()


  transactionMetricsUpdate$: Observable<GetMetricsDashboardServiceResponse> = this.transactionMetricsSource.asObservable();
  transactionRecentUpdate$: Observable<TransactionI[]> = this.transactionRecentsSource.asObservable();
  transactionThreeBiggestUpdate$: Observable<Expenses[]> = this.transactionThreeBiggestExpensesSource.asObservable();


  constructor(
    private httpClient: HttpClient
  ) {

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

  loadThreeBiggestExpenses() {
    this.httpClient.get<Expenses[]>("api/transactions/three_biggest_expenses", {
      headers: {
        "Authorization": `${localStorage.getItem("Authorization")}`
      }
    }).subscribe(res => {
      this.transactionThreeBiggestExpensesSource.next(res);
    })
  }



  notifyTransactionsUpdated(metrics: GetMetricsDashboardServiceResponse): void {
    this.transactionMetricsSource.next(metrics)
  }

  notifyNewTransactionsUpdated(transactions: TransactionI[]) {
    this.transactionRecentsSource.next(transactions)
  }

}
