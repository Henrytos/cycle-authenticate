import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticateUserService } from '../services/authenticate-user-service';
import { toast } from 'ngx-sonner';

export const tokenJwtGuardGuard: CanActivateFn = (route, state) => {
  const authenticateUserService = inject(AuthenticateUserService);
  const router = inject(Router)

  if (!authenticateUserService.isLoggedIn()) {
    toast.error("Por favor faça o login antes de entrar na aplicação")
    return router.navigate(["/login"])
  }

  return true;
};
