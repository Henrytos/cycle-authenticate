import { TestBed } from '@angular/core/testing';

import { GetProfileUserService } from './get-profile-user-service';

describe('GetProfileUserService', () => {
  let service: GetProfileUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetProfileUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
