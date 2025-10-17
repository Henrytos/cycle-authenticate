import { ChangeDetectionStrategy, Component, Inject, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ɵInternalFormsSharedModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogClose,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { NgxMaskDirective } from 'ngx-mask';
import { CreateNewTransactionService } from '../../services/create-new-transaction-service';
import { toast } from 'ngx-sonner';
import { TransactionStateService } from '../../services/transaction-state-service';
import { GetMetricsDashboardService } from '../../services/get-metrics-dashboard-service';
import { TransactionI } from '../../pages/dashboard/dashboard';

@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: 'create-new-transaction-dialog-form.html',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule,
    ɵInternalFormsSharedModule,
    ReactiveFormsModule,
    NgxMaskDirective,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: 'create-new-transaction-dialog-form.scss',
})
export class CreateNewTransactionDialogForm implements OnInit {
  createTransactionForm: FormGroup;


  toast = toast;
  constructor(
    builder: FormBuilder,
    private createNewTransactionService: CreateNewTransactionService,
    private transactionStateService: TransactionStateService,
    private getMetricsDashboardService: GetMetricsDashboardService,
    @Inject(MAT_DIALOG_DATA) public data: {
      isUpdated: boolean;
      transactionDefault: TransactionI
    }
  ) {

    if (this.data.transactionDefault) {
      this.createTransactionForm = builder.group({
        title: [this.data.transactionDefault.title, [Validators.required]],
        value: [this.data.transactionDefault.value, [Validators.required]],
        typeTransaction: [this.data.transactionDefault.typeTransaction, [Validators.required]],
        methodPayment: [this.data.transactionDefault.methodPayment, [Validators.required]],
        dateOfPayment: [this.data.transactionDefault.dateOfPayment, [Validators.required]],
      });
    } else {
      const date = new Date();
      const currentDate = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;

      this.createTransactionForm = builder.group({
        title: ['', [Validators.required]],
        value: ['', [Validators.required]],
        typeTransaction: ['', [Validators.required]],
        methodPayment: ['', [Validators.required]],
        dateOfPayment: [currentDate, [Validators.required]],
      });
    }
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }



  createTransactionFormSubmit() {
    if (this.data.isUpdated) {
      if (this.createTransactionForm.valid) {
        const data = this.createTransactionForm.value;
        console.log(data)
        this.transactionStateService.updateTransaction({
          ...data,
          id: this.data.transactionDefault.id,
          senderId: this.data.transactionDefault.senderId,
        }).subscribe(res => {
          this.getMetricsDashboardService.execute().subscribe((res) => {
            this.transactionStateService.notifyTransactionsUpdated(res);
          });

          this.transactionStateService.loadThreeBiggestExpenses();
          this.transactionStateService.loadTransactions();
          this.transactionStateService.loadAllTransactions();
          toast.success("Sucesso em atualizar")

        }, () => {
          this.toast.error('Erro em Atualizar Transação');

        })
      } else {
        toast.error("Por favor prencha todos os campos corretamente, Transação não cadastrada")
      }
    } else {
      if (this.createTransactionForm.valid) {
        const data = this.createTransactionForm.value;
        console.log(data);
        this.createNewTransactionService.execute(data).subscribe(
          (res) => {
            this.toast.success('Transação criada com sucesso');

            this.getMetricsDashboardService.execute().subscribe((res) => {
              this.transactionStateService.notifyTransactionsUpdated(res);
            });

            this.transactionStateService.loadThreeBiggestExpenses();
            this.transactionStateService.loadTransactions();
            this.transactionStateService.loadAllTransactions();
          },
          () => {
            this.toast.error('Erro em Criar Transação');
          }
        );
      } else {
        toast.error("Por favor prencha todos os campos corretamente, Transação não cadastrada")
      }
    }
  }

}
