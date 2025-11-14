import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, ReplaySubject} from "rxjs";
import {MensaModel} from "../dto/mensa.model";

@Injectable({
  providedIn: 'root'
})
export class MensaService {

  private readonly http: HttpClient = inject(HttpClient);

  private selectedMensa: ReplaySubject<MensaModel | undefined> = new ReplaySubject<MensaModel | undefined>(1);

  baseUrl: string = environment.base_url;
  endpoint: string = 'user/mensa';

  public getMensaOfUser(): Observable<MensaModel> {
    return this.http.get<MensaModel>(this.baseUrl + '/' + this.endpoint, {
      responseType: 'json',
    });
  }

  public saveMensaOfUser(mensa: MensaModel): Observable<MensaModel> {
    return this.http.post<MensaModel>(this.baseUrl + '/' + this.endpoint, mensa, {
      responseType: 'json',
    });
  }

  public getAllMensas(): Observable<MensaModel[]> {
    return this.http.get<MensaModel[]>(this.baseUrl + '/mensa', {
      responseType: 'json',
    });
  }

  public getMensaById(id: number): Observable<MensaModel> {
    return this.http.get<MensaModel>(this.baseUrl + '/mensa/' + id, {
      responseType: 'json',
    });
  }

  public getSelectedMensaSubject(): ReplaySubject<MensaModel | undefined> {
    return this.selectedMensa;
  }

  public updateSelectedMensa(mensa?: MensaModel) {
    this.selectedMensa.next(mensa);
  }
}
