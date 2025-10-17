import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Dashboard } from './pages/dashboard/dashboard';
import { tokenJwtGuardGuard } from './guards/token-jwt-guard-guard';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptorInterceptor } from './interceptors/token-interceptor-interceptor';
import { GetMetricsDashboardService } from './services/get-metrics-dashboard-service';
import { TransactionStateService } from './services/transaction-state-service';
import { GetProfileUserService } from './services/get-profile-user-service';
import { CreateNewTransactionService } from './services/create-new-transaction-service';
import { Transactions } from './pages/transactions/transactions';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: "login",
    component: Login
  },
  {
    path: "register",
    component: Register
  },
  {
    path: "dashboard",
    component: Dashboard,
    canActivate: [tokenJwtGuardGuard],
    providers: [
      provideHttpClient(
        withInterceptors([
          tokenInterceptorInterceptor
        ]
        )
      ),
      GetMetricsDashboardService,
      TransactionStateService,
      GetProfileUserService,
      CreateNewTransactionService
    ]
  }, 
  {
    path: "transactions",
    component: Transactions,
    canActivate: [tokenJwtGuardGuard],
    providers: [
      provideHttpClient(
        withInterceptors([
          tokenInterceptorInterceptor
        ]
        )
      ),
      GetMetricsDashboardService,
      TransactionStateService,
      GetProfileUserService,
      CreateNewTransactionService
    ]
  }
];
