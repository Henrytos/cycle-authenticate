import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Title } from '../../components/title/title';
import { Logo } from '../../components/logo/logo';
import { AuthenticateUserService } from '../../services/authenticate-user-service';
import { toast } from 'ngx-sonner';
@Component({
  selector: 'app-login',
  imports: [RouterOutlet, ReactiveFormsModule, RouterLink, Title, Logo],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  loginForm: FormGroup;
  protected readonly toast = toast;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticateUserService: AuthenticateUserService
  ) {
    this.loginForm = formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  public loginFormSubmit() {
    const { email, password } = this.loginForm.value;

    if (this.loginForm.valid) {
      this.authenticateUserService
        .execute({
          email,
          password,
        })
        .subscribe({
          next: (value) => {
            this.authenticateUserService.setToken(value.token);

            this.toast.success('Sucesso na autenticação');
            this.router.navigate(['/dashboard']);
          },
          error: (err) => {
            const { message } = err.error;

            this.toast.error(message);
          },
        });
    } else {
      this.toast.warning('Por favor preencha o formulário corretamente');
      this.loginForm.markAllAsTouched();
    }
  }
}
