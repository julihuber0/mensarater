import { Component, ChangeDetectionStrategy } from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {NgxSkeletonLoaderModule} from "ngx-skeleton-loader";

@Component({
  selector: 'app-skeleton-card',
  imports: [MatCardModule, NgxSkeletonLoaderModule],
  templateUrl: './skeleton-card.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './skeleton-card.component.scss'
})
export class SkeletonCardComponent {

}
