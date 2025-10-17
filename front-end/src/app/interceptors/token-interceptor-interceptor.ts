import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticateUserService } from '../services/authenticate-user-service';
import { catchError, throwError } from 'rxjs';
import { toast } from 'ngx-sonner';
import { Router } from '@angular/router';

const PUBLIC_URLS = ['/login', '/register'];

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticateUserService = inject(AuthenticateUserService);
  const router = inject(Router);
  const isPublicUrl = PUBLIC_URLS.some((url) => router.url.includes(url));

  let requestClone = req;

  const token = authenticateUserService.getToken();

  if (!isPublicUrl && token) {
    requestClone = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  if (!isPublicUrl && !token) {
    router.navigate(['/login']);
  }

  console.log(isPublicUrl)

  return next(requestClone).pipe(
    catchError((error) => {
      if (error instanceof HttpErrorResponse) {
        if (error.status === 401 && !isPublicUrl) {
          toast.error('Sessão expirada ou não autorizada. Por favor, faça login novamente.');
          authenticateUserService.logout();
          router.navigate(['/login']);
        }
      }

      return throwError(() => error);
    })
  );
};
