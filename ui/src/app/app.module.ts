import {LOCALE_ID, NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import {RouterModule} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";
import {ToastrModule} from "ngx-toastr";
import {HeaderComponent} from "./pages/header/header.component";
import {AppRoutingModule} from "./app-routing.module";
import {
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  includeBearerTokenInterceptor,
  provideKeycloak
} from "keycloak-angular";
import {keycloakConfig} from "./config/keycloak-init.factory";
import {deployedUrlCondition, localhostUrlCondition} from "./config/url-condition.config";
import localeDe from '@angular/common/locales/de';
import {registerLocaleData} from "@angular/common";

registerLocaleData(localeDe);

@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    RouterModule,
    HeaderComponent,
    ToastrModule.forRoot({
      positionClass: "toast-top-center"
    })
  ],
  providers: [provideHttpClient(withInterceptors([includeBearerTokenInterceptor])),
    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [localhostUrlCondition, deployedUrlCondition]
    },
    {
      provide: LOCALE_ID,
      useValue: 'de-DE'
    },
    provideKeycloak(keycloakConfig),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
