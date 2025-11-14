import {ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from "@angular/router";
import {AuthGuardData, createAuthGuard} from "keycloak-angular";
import {inject} from "@angular/core";
import {AuthService} from "../service/auth.service";

const isAccessAllowed = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
  authData: AuthGuardData
): Promise<boolean> => {
  const { authenticated, grantedRoles } = authData;

  const requiredRole = route.data['role'];
  if (!requiredRole) return false;

  const hasRequiredRole = (role: string): boolean => {
    return Object.values(grantedRoles.resourceRoles).some((roles) => roles.includes(role));
  }

  const authService = inject(AuthService);

  if (!authenticated) {
    authService.login({
      redirectUri: window.location.origin + state.url
    });
  }

  return authenticated && hasRequiredRole(requiredRole);
}

export const canRate = createAuthGuard<CanActivateFn>(isAccessAllowed);
