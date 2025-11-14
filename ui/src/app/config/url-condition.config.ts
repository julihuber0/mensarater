import {createInterceptorCondition, IncludeBearerTokenCondition} from "keycloak-angular";

export const localhostUrlCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
  urlPattern: /^(http:\/\/localhost:8080)(\/ratings\/user.*|\/user\/mensa.*)?$/i,
  bearerPrefix: 'Bearer'
});

export const deployedUrlCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
  urlPattern: /^(https:\/\/api\.mensa\.jules-labs\.de)(\/ratings\/user.*|\/user\/mensa.*)?$/i,
  bearerPrefix: 'Bearer'
});
