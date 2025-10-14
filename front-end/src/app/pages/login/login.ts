import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Title } from '../../components/title/title';
import { Logo } from "../../components/logo/logo";

@Component({
  selector: 'app-login',
  imports: [RouterOutlet, ReactiveFormsModule, RouterLink, Title, Logo],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router
  ) {

    this.loginForm = formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      }
    );
  }

  public loginFormSubmit() {
    const { email, password } = this.loginForm.value;

    if (this.loginForm.valid) {
      this.loginForm.reset()
      this.router.navigate(['/dashboard'])
    }
  }
}
