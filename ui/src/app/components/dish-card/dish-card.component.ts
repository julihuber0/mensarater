import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {DishModel} from "../../dto/dish.model";
import {MatButton} from "@angular/material/button";
import {MatChip} from "@angular/material/chips";
import {PricePipe} from "../../pipes/price.pipe";
import {RatingPipe} from "../../pipes/rating.pipe";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-dish-card',
  imports: [
    MatCardModule,
    MatButton,
    MatChip,
    PricePipe,
    RatingPipe,
    NgClass,
  ],
  templateUrl: './dish-card.component.html',
  styleUrl: './dish-card.component.scss'
})
export class DishCardComponent {

  @Input() dish!: DishModel;
  @Input() canRate: boolean = false;

  @Output() rateButtonEmitter: EventEmitter<DishModel> = new EventEmitter<DishModel>()

  get ratingClass(): string {
    const prefix = 'rating';
    if (this.dish.rating === -1) return prefix;
    if (this.dish.rating < 3) return prefix + '-2';
    if (this.dish.rating < 5) return prefix + '-4';
    if (this.dish.rating < 7) return prefix + '-6';
    if (this.dish.rating < 9) return prefix + '-8';
    return prefix + '-10';
  }

  onClickRate() {
    this.rateButtonEmitter.emit(this.dish);
  }

}
