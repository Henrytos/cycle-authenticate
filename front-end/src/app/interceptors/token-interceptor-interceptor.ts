import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticateUserService } from '../services/authenticate-user-service';
import { catchError, throwError } from 'rxjs';
import { toast } from 'ngx-sonner';
import { Router } from '@angular/router';

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticateUserService = inject(AuthenticateUserService)
  const router = inject(Router)

  const requestClone = req.clone({
    setHeaders: {
      Authorization: authenticateUserService.getToken() as string
    }
  });


  return next(requestClone).pipe(
    catchError((error) => {
      if (error.status == 401) {
        toast.error("Usuario não autorizado")
        authenticateUserService.logout()
        router.navigate(["/login"])
      }

      return throwError("Usuario não autorizado")
    })
  )
};
