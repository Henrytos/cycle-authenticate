import { TestBed } from '@angular/core/testing';

import { CreateNewTransactionService } from './create-new-transaction-service';

describe('CreateNewTransactionService', () => {
  let service: CreateNewTransactionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateNewTransactionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
