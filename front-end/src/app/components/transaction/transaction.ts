import { Component, DEFAULT_CURRENCY_CODE, Input, LOCALE_ID } from '@angular/core';
import { TransactionI } from '../../pages/dashboard/dashboard';
import { LucideAngularModule } from 'lucide-angular';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-transaction',
  imports: [LucideAngularModule, CurrencyPipe, DatePipe],
  templateUrl: './transaction.html',
  styleUrl: './transaction.scss',
  providers: [
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL' },
    {
      provide: LOCALE_ID,
      useValue: 'pt-BR',
    },
  ],
})
export class Transaction {
  @Input() transaction!: TransactionI;
}
