import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'rating'
})
export class RatingPipe implements PipeTransform {

  transform(value: number): string {
    if (value == -1) return '-'
    return String(value).replace('.', ',')
  }

}
