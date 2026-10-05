import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewComponent} from './overview.component';
import {provideRouter} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DishService} from "../../service/dish.service";
import {MensaService} from "../../service/mensa.service";
import {AuthService} from "../../service/auth.service";
import {EMPTY} from "rxjs";

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let fixture: ComponentFixture<OverviewComponent>;

  let mockMatDialog = {open: vi.fn()};
  let mockDishService = {getDishes: vi.fn()};
  let mockMensaService = {getSelectedMensaSubject: vi.fn(() => EMPTY)};
  let mockAuthService = {login: vi.fn()};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverviewComponent],
      providers: [provideRouter([]),
        {provide: MatDialog, useValue: mockMatDialog},
        {provide: DishService, useValue: mockDishService},
        {provide: MensaService, useValue: mockMensaService},
        {provide: AuthService, useValue: mockAuthService}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(OverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
