import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [RouterOutlet, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder
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

    if (this.loginForm.valid)
      this.loginForm.reset()

  }
}
