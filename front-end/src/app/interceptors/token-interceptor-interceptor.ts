import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticateUserService } from '../services/authenticate-user-service';

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticateUserService = inject(AuthenticateUserService)

  console.log(authenticateUserService.getToken())
  console.log("AQUIIIIIIIIIIIIII")
  const requestClone = req.clone({
    setHeaders: {
      Authorization: authenticateUserService.getToken() as string
    }
  });


  return next(requestClone)
};
