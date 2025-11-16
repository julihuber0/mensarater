import {ComponentFixture, TestBed} from '@angular/core/testing';

import {OverviewComponent} from './overview.component';
import {provideRouter} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {DishService} from "../../service/dish.service";
import {MensaService} from "../../service/mensa.service";
import {AuthService} from "../../service/auth.service";

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let fixture: ComponentFixture<OverviewComponent>;

  let mockMatDialog: jasmine.SpyObj<MatDialog> = jasmine.createSpyObj('MatDialog', ['open']);
  let mockDishService: jasmine.SpyObj<DishService> = jasmine.createSpyObj('DishService', ['getDishes']);
  let mockMensaService: jasmine.SpyObj<MensaService> = jasmine.createSpyObj('MensaService', ['getMensaOfUser']);
  let mockAuthService: jasmine.SpyObj<AuthService> = jasmine.createSpyObj('AuthService', ['login']);

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
