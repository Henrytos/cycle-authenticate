import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { GetMetricsDashboardServiceResponse } from './get-metrics-dashboard-service';

@Injectable({
  providedIn: 'root'
})
export class TransactionStateService {
  private transactionMetricsSource = new Subject<GetMetricsDashboardServiceResponse>()

  transactionMetricsUpdate$: Observable<GetMetricsDashboardServiceResponse> = this.transactionMetricsSource.asObservable();


  notifyTransactionsUpdated(metrics: GetMetricsDashboardServiceResponse): void {
    this.transactionMetricsSource.next(metrics)
  }
}
