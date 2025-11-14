import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DishModel} from "../../dto/dish.model";
import {DishService} from "../../service/dish.service";
import {MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSliderModule} from "@angular/material/slider";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";

@Component({
  selector: 'app-rating-dialog',
  templateUrl: './rating-dialog.component.html',
  imports: [MatDialogModule, ReactiveFormsModule, MatFormFieldModule, MatSliderModule, MatCheckboxModule, MatButtonModule, MatCardModule],
  styleUrl: './rating-dialog.component.scss',
})
export class RatingDialogComponent implements OnInit {

  private readonly formBuilder: FormBuilder = inject(FormBuilder);
  private readonly dishService: DishService = inject(DishService);

  dish!: DishModel;

  ratingForm: FormGroup = this.formBuilder.group({});

  ngOnInit(): void {
    this.ratingForm = this.formBuilder.group({
      rating: [this._getInitialSliderRating(), [Validators.required, Validators.min(0), Validators.max(10)]],
      warning: [this.dish.warning, [Validators.required]],
    });
  }

  onSave() {
    if (!this.ratingForm.invalid) {
      const inputs = this.ratingForm.value;
      Object.assign(this.dish, inputs);
      this.dishService.saveDishRating(this.dish).subscribe();
    }
  }

  private _getInitialSliderRating() {
    if (this.dish.rating != -1) return this.dish.rating;
    return 5;
  }
}
