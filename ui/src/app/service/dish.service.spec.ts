import { TestBed } from '@angular/core/testing';

import { DishService } from './dish.service';
import {provideHttpClient, withXhr} from "@angular/common/http";
import {provideHttpClientTesting} from "@angular/common/http/testing";

describe('DishService', () => {
  let service: DishService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(withXhr()), provideHttpClientTesting()]
    });
    service = TestBed.inject(DishService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
