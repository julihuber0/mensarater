import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingDialogComponent } from './rating-dialog.component';
import {FormBuilder} from "@angular/forms";
import {DishService} from "../../service/dish.service";

describe('RatingDialogComponent', () => {
  let component: RatingDialogComponent;
  let fixture: ComponentFixture<RatingDialogComponent>;

  let dishServiceMock: jasmine.SpyObj<DishService> = jasmine.createSpyObj('DishService', ['saveDishRating']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RatingDialogComponent],
      providers: [
        {provide: DishService, useValue: dishServiceMock},
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatingDialogComponent);
    component = fixture.componentInstance;
    component.dish = {user: "yeet", dishName: 'Test', category: 'main', price: 1, rating: 5, warning: false};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
