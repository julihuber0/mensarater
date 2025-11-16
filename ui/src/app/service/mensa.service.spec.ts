import { TestBed } from '@angular/core/testing';

import { MensaService } from './mensa.service';
import {provideHttpClient} from "@angular/common/http";
import {provideHttpClientTesting} from "@angular/common/http/testing";

describe('MensaService', () => {
  let service: MensaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(MensaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
