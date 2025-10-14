import { Component } from '@angular/core';
import { TransactionI } from '../../pages/dashboard/dashboard';
import { Transaction } from "../transaction/transaction";

@Component({
  selector: 'app-list-transaction',
  imports: [Transaction],
  templateUrl: './list-transaction.html',
  styleUrl: './list-transaction.scss'
})
export class ListTransaction {

  transactions: TransactionI[] = [
    {
      id: 1,
      title: "Salario",
      date: "15 Nov, 2024",
      value: "R$ 3.900",
      type: "entry",
      typeMethod: "pix"
    },
    {
      id: 2,
      title: "Academia",
      date: "14 Nov, 2024",
      value: "R$ 120",
      type: "spent",
      typeMethod: "credito"
    },
    {
      id: 3,
      title: "Aluguel",
      date: "13 Nov, 2024",
      value: "R$ 1.800",
      type: "spent",
      typeMethod: "boleto"
    },
    {
      id: 4,
      title: "Freelancing",
      date: "12 Nov, 2024",
      value: "R$ 3.000",
      type: "entry",
      typeMethod: "pix"
    },
    {
      id: 5,
      title: "Salario",
      date: "15 Nov, 2024",
      value: "R$ 3.900",
      type: "entry",
      typeMethod: "pix"
    },
    {
      id: 6,
      title: "Academia",
      date: "14 Nov, 2024",
      value: "R$ 120",
      type: "spent",
      typeMethod: "credito"
    },
    {
      id: 7,
      title: "Aluguel",
      date: "13 Nov, 2024",
      value: "R$ 1.800",
      type: "spent",
      typeMethod: "boleto"
    },
    {
      id: 8,
      title: "Freelancing",
      date: "12 Nov, 2024",
      value: "R$ 3.000",
      type: "entry",
      typeMethod: "pix"
    }
  ]

}
