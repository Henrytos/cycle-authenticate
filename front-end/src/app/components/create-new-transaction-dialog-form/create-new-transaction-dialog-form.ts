import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from "@angular/material/dialog";
import { NgxMaskDirective } from "ngx-mask";
import { CreateNewTransactionService } from "../../services/create-new-transaction-service";
import { toast } from "ngx-sonner";
import { TransactionStateService } from "../../services/transaction-state-service";
import { GetMetricsDashboardService } from "../../services/get-metrics-dashboard-service";


@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: 'create-new-transaction-dialog-form.html',
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule, ɵInternalFormsSharedModule, ReactiveFormsModule, NgxMaskDirective],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: 'create-new-transaction-dialog-form.scss'
})
export class CreateNewTransactionDialogForm implements OnInit {
  createTransactionForm: FormGroup;
  toast = toast
  constructor(
    builder: FormBuilder,
    private createNewTransactionService: CreateNewTransactionService,
    private transactionStateService: TransactionStateService,
    private getMetricsDashboardService: GetMetricsDashboardService
  ) {
    this.createTransactionForm = builder.group({
      title: ['', [Validators.required]],
      value: ['', [Validators.required]],
      typeTransaction: ['', [Validators.required]],
      methodPayment: ['', [Validators.required]],
      dateOfPayment: ['', [Validators.required]],
    })
  }

  ngOnInit(): void {
    const date = new Date();
    const currentDate = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
    this.createTransactionForm.get("dateOfPayment")?.setValue(currentDate)
  }


  createTransactionFormSubmit() {


    if (this.createTransactionForm.valid) {
      const data = this.createTransactionForm.value;
      console.log(data)
      this.createNewTransactionService.execute(data).subscribe((res) => {
        console.log(res)
        this.toast.success("Transação criada com sucesso")

        this.getMetricsDashboardService.execute().subscribe(res => {
          this.transactionStateService.notifyTransactionsUpdated(res)
        })

        this.transactionStateService.loadTransactions()
      }, () => {
        this.toast.error("Transação Não Feita")
      });
    }
  }

}
