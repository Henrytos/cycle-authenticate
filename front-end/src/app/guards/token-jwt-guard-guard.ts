import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticateUserService } from '../services/authenticate-user-service';

export const tokenJwtGuardGuard: CanActivateFn = (route, state) => {
  const authenticateUserService = inject(AuthenticateUserService);
  const router = inject(Router)

  if (!authenticateUserService.isLoggedIn()) {
    return router.navigate(["/login"])
  }

  return true;
};
