import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Title } from "../../components/title/title";
import { Logo } from "../../components/logo/logo";
import { CreateAccountService } from '../../services/create-account-service';
import { toast } from 'ngx-sonner';
import { tap } from 'rxjs';

@Component({
  selector: 'app-register',
  imports: [RouterOutlet, ReactiveFormsModule, RouterLink, Title, Logo],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm: FormGroup;
  protected readonly toast = toast;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private createAccountService: CreateAccountService
  ) {

    this.registerForm = formBuilder.group(
      {
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        dateOfBirth: ['', [Validators.required]]
      }
    );
  }

  public loginFormSubmit() {
    const { username,
      email,
      password,
      dateOfBirth } = this.registerForm.value;

    if (this.registerForm.valid) {
      this.createAccountService.execute({
        username,
        email,
        password,
        dateOfBirth
      }).pipe(
        tap(() => {
          this.router.navigate(["/login"])
        })
      ).subscribe({
        next() {
          toast.success("Sucesso em cadastrar novo usuario")
        },
        error(err) {
          const { message } = err.error;

          toast.error(message)
        }
      })

    } else {
      this.toast.warning("Erro no fomulario, por favor preencha todos os campos")
    }

    this.registerForm.markAllAsTouched()

  }
}
