import { TestBed } from '@angular/core/testing';

import { GetMetricsDashboardService } from './get-metrics-dashboard-service';

describe('GetMetricsDashboardService', () => {
  let service: GetMetricsDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetMetricsDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
