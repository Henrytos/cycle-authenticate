import { Component, DEFAULT_CURRENCY_CODE, Input } from '@angular/core';
import { TransactionI } from '../../pages/dashboard/dashboard';
import { LucideAngularModule } from "lucide-angular";
import { CurrencyPipe, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt);


@Component({
  selector: 'app-transaction',
  imports: [LucideAngularModule, CurrencyPipe],
  templateUrl: './transaction.html',
  styleUrl: './transaction.scss',
  providers: [
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL' }
  ],

})
export class Transaction {
  @Input() transaction!: TransactionI;


}
