import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { ArrowDownUp, BadgeDollarSign, CreditCard, Eye, Home, LucideAngularModule, Menu, PiggyBank, Tally4, TrendingDown, TrendingUp, UserCheck, Wallet } from 'lucide-angular';
import { provideNgxMask, NgxMaskDirective } from 'ngx-mask';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    importProvidersFrom(
      LucideAngularModule.pick({ CreditCard, Tally4, BadgeDollarSign, Wallet, PiggyBank, TrendingUp, TrendingDown, Eye, ArrowDownUp })
    ),
    provideNgxMask()
  ]
};
