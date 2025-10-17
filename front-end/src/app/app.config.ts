import {
  ApplicationConfig,
  importProvidersFrom,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {
  ArrowDownUp,
  BadgeDollarSign,
  CreditCard,
  Eye,
  Home,
  LogOut,
  LucideAngularModule,
  Menu,
  PiggyBank,
  Tally4,
  Trash2,
  TrendingDown,
  TrendingUp,
  UserCheck,
  Wallet,
} from 'lucide-angular';
import { provideNgxMask, NgxMaskDirective } from 'ngx-mask';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptorInterceptor } from './interceptors/token-interceptor-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    importProvidersFrom(
      LucideAngularModule.pick({
        CreditCard,
        Tally4,
        BadgeDollarSign,
        Wallet,
        PiggyBank,
        TrendingUp,
        TrendingDown,
        Eye,
        ArrowDownUp,
        LogOut,
        Trash2,
      })
    ),
    provideNgxMask(),
    provideHttpClient(withInterceptors([tokenInterceptorInterceptor])),
  ],
};
