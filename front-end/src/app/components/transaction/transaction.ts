import { Component, Input } from '@angular/core';
import { TransactionI } from '../../pages/dashboard/dashboard';
import { LucideAngularModule } from "lucide-angular";

@Component({
  selector: 'app-transaction',
  imports: [LucideAngularModule],
  templateUrl: './transaction.html',
  styleUrl: './transaction.scss'
})
export class Transaction {
  @Input() transaction!: TransactionI;


}
