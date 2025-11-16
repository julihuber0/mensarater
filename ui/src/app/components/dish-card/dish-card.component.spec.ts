import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DishCardComponent } from './dish-card.component';
import {DishModel} from "../../dto/dish.model";

describe('DishCardComponent', () => {
  let component: DishCardComponent;
  let fixture: ComponentFixture<DishCardComponent>;

  const dish: DishModel = {user: "yeet", dishName: 'Test', category: 'main', price: 1, rating: 5, warning: false};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DishCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DishCardComponent);
    component = fixture.componentInstance;
    component.dish = dish;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
