import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from "@angular/material/dialog";
import { NgxMaskDirective } from "ngx-mask";


@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: 'create-new-transaction-dialog-form.html',
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule, ɵInternalFormsSharedModule, ReactiveFormsModule, NgxMaskDirective],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: 'create-new-transaction-dialog-form.scss'
})
export class CreateNewTransactionDialogForm implements OnInit {


  createTransactionForm: FormGroup;
  constructor(builder: FormBuilder) {
    this.createTransactionForm = builder.group({
      title: ['', [Validators.required]],
      valueInReal: ['', [Validators.required]],
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

    console.log(this.createTransactionForm.value)
  }

}
