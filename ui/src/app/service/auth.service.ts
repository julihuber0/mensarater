import {inject, Injectable, signal, Signal, WritableSignal} from "@angular/core";
import Keycloak, {KeycloakLoginOptions, KeycloakUserInfo} from "keycloak-js";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly keycloak: Keycloak = inject(Keycloak);

  private userName: WritableSignal<string> = signal<string>('');
  private email: WritableSignal<string> = signal<string>('');

  constructor() {
    if (this.loggedIn) {
      this.getUserInfo().then((info) => {
        this.userName.set(info.displayname ?? '');
        this.email.set(info.email ?? '');
      });
    }
  }

  getUserName(): Signal<string> {
    return this.userName;
  }

  get loggedIn(): boolean {
    return this.keycloak.authenticated ?? false;
  }

  public login(options?: KeycloakLoginOptions) {
    this.keycloak.login(options);
  }

  public logout(): void {
    this.keycloak.logout({
      redirectUri: window.location.origin
    });
  }

  public async getUserProfile() {
    return this.keycloak.loadUserProfile();
  }

  public async getUserInfo() {
    const keycloakUser: KeycloakUserInfo = await this.keycloak.loadUserInfo();
    return {
      displayname: keycloakUser['preferred_username'],
      email: keycloakUser['email'],
    }
  }
}
