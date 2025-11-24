import {Component, inject, OnInit} from '@angular/core';
import {MensaService} from "./service/mensa.service";
import {lastValueFrom} from "rxjs";
import {AuthService} from "./service/auth.service";
import {MensaModel} from "./dto/mensa.model";
import {RouterModule} from "@angular/router";
import {HeaderComponent} from "./pages/header/header.component";
import {FooterComponent} from "./components/footer/footer.component";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    imports: [
      RouterModule,
      HeaderComponent,
      FooterComponent
    ],
    styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  private mensaService: MensaService = inject(MensaService);
  private authService: AuthService = inject(AuthService);

  async ngOnInit() {
    const selectedMensa = await this.getSelectedMensa();
    this.mensaService.updateSelectedMensa(selectedMensa);
  }

  private async getSelectedMensa(): Promise<MensaModel | undefined> {
    let mensa: MensaModel | undefined;
    if (this.authService.loggedIn) {
      mensa = await lastValueFrom(this.mensaService.getMensaOfUser());
      if (!mensa) {
        mensa = await this.loadMensaFromBrowser();
        if (mensa) {
          await lastValueFrom(this.mensaService.saveMensaOfUser(mensa));
        }
      }
    } else {
      mensa = await this.loadMensaFromBrowser();
    }
    return mensa;
  }

  private async loadMensaFromBrowser(): Promise<MensaModel | undefined> {
    const storedMensa = localStorage.getItem('mensarater_mensa_id');
    if (storedMensa) {
      const storedMensaId = parseInt(storedMensa);
      if (storedMensaId) {
        return await lastValueFrom(this.mensaService.getMensaById(storedMensaId));
      }
    }
    return undefined;
  }
}
