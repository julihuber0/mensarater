import {inject, Injectable} from '@angular/core';
import {DishModel} from "../dto/dish.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DishService {

  private readonly http: HttpClient = inject(HttpClient);

  baseUrl: string = environment.base_url;
  endpoint: string = 'ratings'

  public getAverageDishRating(openMensaId: number, date: Date): Observable<DishModel[]> {
    const params = new HttpParams()
      .set('openMensaId', openMensaId)
      .set('date', date.toISOString());
    return this.http.get<DishModel[]>(this.baseUrl + '/' + this.endpoint, {
      responseType: 'json',
      params: params
    });
  }

  public getUserDishRating(date: Date): Observable<DishModel[]> {
    const params = new HttpParams().set('date', date.toISOString());
    return this.http.get<DishModel[]>(this.baseUrl + '/' + this.endpoint + '/user', {
      responseType: "json",
      params: params
    });
  }

  public saveDishRating(dish: DishModel): Observable<DishModel> {
    return this.http.post<DishModel>(this.baseUrl + '/' + this.endpoint + '/user', dish, {
      responseType: "json"
    });
  }
}
