import { ChangeDetectionStrategy, Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from "@angular/material/dialog";


@Component({
  selector: 'dialog-elements-example-dialog',
  templateUrl: 'create-new-transaction-dialog-form.html',
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule, ɵInternalFormsSharedModule, ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: 'create-new-transaction-dialog-form.scss'
})
export class CreateNewTransactionDialogForm {


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


  createTransactionFormSubmit() {

    console.log(this.createTransactionForm.value)
  }

}
