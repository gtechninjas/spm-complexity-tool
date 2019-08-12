import { TestBed } from '@angular/core/testing';

import { OperatorserviceService } from './operatorservice.service';

describe('operatorserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OperatorserviceService = TestBed.get(OperatorserviceService);
    expect(service).toBeTruthy();
  });
});
