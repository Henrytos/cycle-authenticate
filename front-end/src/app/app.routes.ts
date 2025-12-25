import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Dashboard } from './pages/dashboard/dashboard';
import { tokenJwtGuardGuard } from './guards/token-jwt-guard-guard';
import { Transactions } from './pages/transactions/transactions';
import { ForgotPassword } from './pages/forgot-password/forgot-password';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: 'login',
    title: 'Entrar',
    component: Login,
  },
  {
    path: 'register',
    title: 'Cadastre-se',
    component: Register,
  },
  {
    path: 'dashboard',
    title: 'Dashboard',
    component: Dashboard,
    canActivate: [tokenJwtGuardGuard],
  },
  {
    path: 'transactions',
    title: 'Transações',
    component: Transactions,
    canActivate: [tokenJwtGuardGuard],
  },
  {
    path: 'forgot-password',
    title: 'Esqueci minha senha',
    component: ForgotPassword
  }
];
