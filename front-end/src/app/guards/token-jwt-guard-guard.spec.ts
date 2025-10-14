import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { tokenJwtGuardGuard } from './token-jwt-guard-guard';

describe('tokenJwtGuardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => tokenJwtGuardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
