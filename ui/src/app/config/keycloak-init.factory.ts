import {
  AutoRefreshTokenService,
  ProvideKeycloakOptions,
  UserActivityService,
  withAutoRefreshToken
} from "keycloak-angular";

export const keycloakConfig: ProvideKeycloakOptions = {
  config: {
    url: 'https://login.jules-labs.de/',
    realm: 'MensaRater',
    clientId: 'mensarater',
  },
  initOptions: {
    onLoad: 'check-sso',
    checkLoginIframe: false,
  },
  features: [
    withAutoRefreshToken()
  ],
  providers: [AutoRefreshTokenService, UserActivityService]
}
