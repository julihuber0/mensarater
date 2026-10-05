import {ApplicationConfig, LOCALE_ID, provideZonelessChangeDetection} from "@angular/core";
import {provideHttpClient, withInterceptors, withXhr} from "@angular/common/http";
import {
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  includeBearerTokenInterceptor,
  provideKeycloak
} from "keycloak-angular";
import {deployedUrlCondition, localhostUrlCondition} from "./config/url-condition.config";
import {keycloakConfig} from "./config/keycloak-init.factory";
import {provideRouter} from "@angular/router";
import {routes} from "./app.routes";
import {provideHotToastConfig} from "@ngxpert/hot-toast";

export const appConfig: ApplicationConfig = {
  providers: [
    provideZonelessChangeDetection(),
    provideHttpClient(withXhr(), withInterceptors([includeBearerTokenInterceptor])),
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
    provideHotToastConfig(
      {
        position: "top-center"
      }
    )
  ],
}
