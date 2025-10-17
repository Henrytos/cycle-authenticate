import { Component, DEFAULT_CURRENCY_CODE, inject, LOCALE_ID, OnInit, signal } from '@angular/core';
import { Header } from '../../components/header/header';
import { Title } from '../../components/title/title';
import { LucideAngularModule } from 'lucide-angular';
import { MatDialog } from '@angular/material/dialog';
import { CreateNewTransactionDialogForm } from '../../components/create-new-transaction-dialog-form/create-new-transaction-dialog-form';
import { CurrencyPipe, DatePipe, LowerCasePipe } from '@angular/common';
import { TransactionStateService } from '../../services/transaction-state-service';
import { toast } from 'ngx-sonner';
import { TransactionI } from '../dashboard/dashboard';

@Component({
  selector: 'app-transactions',
  imports: [Header, Title, LucideAngularModule, DatePipe, CurrencyPipe],
  templateUrl: './transactions.html',
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'pt-BR',
    },
    {
      provide: DEFAULT_CURRENCY_CODE,
      useValue: 'BRL',
    },
  ],
  styleUrl: './transactions.scss',
})
export class Transactions implements OnInit {
  private readonly toast = toast;

  private readonly dialog = inject(MatDialog);

  transactions = signal<TransactionI[]>([]);

  constructor(private transactionStateService: TransactionStateService) { }
  ngOnInit(): void {
    this.transactionStateService.getAllTransactions().subscribe((transactions) => {
      this.transactions.set(transactions); // update
    });

    this.transactionStateService.transactionStateUpdate$.subscribe((transactions) => {
      this.transactions.set(transactions); // update
    });
  }

  openDialogCreateTransaction() {
    this.dialog.open(CreateNewTransactionDialogForm, {
      data: {
        isUpdated: false
      }
    });
  }

  openDialogUpdatedTransaction(transaction: TransactionI) {
    this.dialog.open(CreateNewTransactionDialogForm, {
      data: {
        isUpdated: true,
        transactionDefault: transaction
      }
    });
  }

  deleteTransaction(id: string) {
    this.transactionStateService.deleteById(id).subscribe(
      () => {
        this.transactions.update((currentTransactions) =>
          currentTransactions.filter((transaction) => transaction.id !== id)
        );

        this.toast.success('Transação excluída com sucesso.');
      },
      () => {
        this.toast.error('Erro ao excluir a transação. Tente novamente mais tarde.');
      }
    );
  }
}
