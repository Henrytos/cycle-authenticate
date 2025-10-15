import { Component, signal } from '@angular/core';
import { TransactionI } from '../../pages/dashboard/dashboard';
import { Transaction } from "../transaction/transaction";
import { TransactionStateService } from '../../services/transaction-state-service';

@Component({
  selector: 'app-list-transaction',
  imports: [Transaction],
  templateUrl: './list-transaction.html',
  styleUrl: './list-transaction.scss'
})
export class ListTransaction {

  transactions = signal([] as TransactionI[])

  constructor(
    private transactionStateService: TransactionStateService
  ) {
    transactionStateService.transactionRecentUpdate$.subscribe((transactions) => {
      this.transactions.update(() => transactions)
    })
  }


}
