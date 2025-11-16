import {ApplicationConfig, LOCALE_ID} from "@angular/core";
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  includeBearerTokenInterceptor,
  provideKeycloak
} from "keycloak-angular";
import {deployedUrlCondition, localhostUrlCondition} from "./config/url-condition.config";
import {keycloakConfig} from "./config/keycloak-init.factory";
import {provideRouter} from "@angular/router";
import {routes} from "./app.routes";
import {provideToastr} from "ngx-toastr";

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([includeBearerTokenInterceptor])),
    {
      provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
      useValue: [localhostUrlCondition, deployedUrlCondition]
    },
    {
      provide: LOCALE_ID,
      useValue: 'de-DE'
    },
    provideKeycloak(keycloakConfig),
    provideRouter(routes),
    provideToastr(
      {
        positionClass: "toast-top-center"
      }
    )
  ],
}
