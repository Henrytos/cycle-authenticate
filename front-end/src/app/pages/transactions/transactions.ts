import { Component, inject } from '@angular/core';
import { Header } from "../../components/header/header";
import { Title } from "../../components/title/title";
import { LucideAngularModule } from "lucide-angular";
import { MatDialog } from '@angular/material/dialog';
import { CreateNewTransactionDialogForm } from '../../components/create-new-transaction-dialog-form/create-new-transaction-dialog-form';

@Component({
  selector: 'app-transactions',
  imports: [Header, Title, LucideAngularModule],
  templateUrl: './transactions.html',
  styleUrl: './transactions.scss'
})
export class Transactions {

  private readonly dialog = inject(MatDialog)

  openDialog() {

    this.dialog.open(CreateNewTransactionDialogForm);
  }
}
