import { Injectable, OnInit } from '@angular/core';
import { Observable, Subject, tap } from 'rxjs';
import { GetMetricsDashboardServiceResponse } from './get-metrics-dashboard-service';
import { Expenses, TransactionI } from '../pages/dashboard/dashboard';
import { HttpClient } from '@angular/common/http';

type TransactionStateServiceResponse = TransactionI[];

@Injectable({
  providedIn: 'root',
})
export class TransactionStateService {
  private transactionMetricsSource = new Subject<GetMetricsDashboardServiceResponse>();
  private transactionRecentsSource = new Subject<TransactionI[]>();
  private transactionThreeBiggestExpensesSource = new Subject<Expenses[]>();
  private transactionStateSource = new Subject<TransactionI[]>();

  transactionMetricsUpdate$: Observable<GetMetricsDashboardServiceResponse> =
    this.transactionMetricsSource.asObservable();
  transactionRecentUpdate$: Observable<TransactionI[]> =
    this.transactionRecentsSource.asObservable();
  transactionThreeBiggestUpdate$: Observable<Expenses[]> =
    this.transactionThreeBiggestExpensesSource.asObservable();
  transactionStateUpdate$: Observable<TransactionI[]> = this.transactionStateSource.asObservable();
  constructor(private httpClient: HttpClient) {}

  loadTransactions() {
    this.httpClient
      .get<TransactionStateServiceResponse>('api/transactions/recents', {
        headers: {
          Authorization: `${localStorage.getItem('Authorization')}`,
        },
      })
      .subscribe((res) => {
        this.transactionRecentsSource.next(res);
      });
  }

  loadThreeBiggestExpenses() {
    this.httpClient.get<Expenses[]>('api/transactions/three_biggest_expenses').subscribe((res) => {
      this.transactionThreeBiggestExpensesSource.next(res);
    });
  }

  loadAllTransactions() {
    this.httpClient.get<TransactionI[]>('api/transactions').subscribe((res) => {
      this.transactionStateSource.next(res);
    });
  }

  notifyTransactionsUpdated(metrics: GetMetricsDashboardServiceResponse): void {
    this.transactionMetricsSource.next(metrics);
  }

  notifyNewTransactionsUpdated(transactions: TransactionI[]) {
    this.transactionRecentsSource.next(transactions);
    this.transactionStateSource.next(transactions);
  }

  deleteById(id: string) {
    return this.httpClient.delete(`api/transactions/${id}`).pipe(
      tap(() => {
        this.loadThreeBiggestExpenses();
        this.loadTransactions();
      })
    );
  }

  getAllTransactions() {
    return this.httpClient.get<TransactionI[]>('api/transactions');
  }
}
