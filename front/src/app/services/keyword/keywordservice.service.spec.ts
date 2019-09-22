import { TestBed } from '@angular/core/testing';

import { KeywordserviceService } from './keywordservice.service';

describe('KeywordserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: KeywordserviceService = TestBed.get(KeywordserviceService);
    expect(service).toBeTruthy();
  });
});
