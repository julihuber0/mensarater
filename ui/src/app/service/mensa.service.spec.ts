import { TestBed } from '@angular/core/testing';

import { MensaService } from './mensa.service';

describe('MensaService', () => {
  let service: MensaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MensaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
