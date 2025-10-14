import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewTransactionDialogForm } from './create-new-transaction-dialog-form';

describe('CreateNewTransactionDialogForm', () => {
  let component: CreateNewTransactionDialogForm;
  let fixture: ComponentFixture<CreateNewTransactionDialogForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateNewTransactionDialogForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateNewTransactionDialogForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
