import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'price'
})
export class PricePipe implements PipeTransform {

  transform(value: number,): string {
    const digits: string[] = String(value).split(".");
    if (digits.length == 1) {
      return digits[0] + ',00 €'
    }
    if (digits[1].length == 1) {
      return digits[0] + ',' + digits[1] + '0 €';
    }
    return digits[0] + ',' + digits[1] + ' €';
  }

}
