import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import {provideHttpClient} from "@angular/common/http";
import {provideHttpClientTesting} from "@angular/common/http/testing";
import {
  provideKeycloak,
  ProvideKeycloakOptions
} from "keycloak-angular";

const keycloakConfigTesting: ProvideKeycloakOptions = {
  config: {
    url: 'https://login.jules-labs.de/',
    realm: 'MensaRater',
    clientId: 'mensarater',
  },
}

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideKeycloak(keycloakConfigTesting)],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
