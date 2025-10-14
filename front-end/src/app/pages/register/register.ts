import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Title } from "../../components/title/title";
import { Logo } from "../../components/logo/logo";

@Component({
  selector: 'app-register',
  imports: [RouterOutlet, ReactiveFormsModule, RouterLink, Title, Logo],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router
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
    console.log({
      username,
      email,
      password,
      dateOfBirth
    })
    if (this.registerForm.valid) {
      this.registerForm.reset()
      this.router.navigate(['/login'])

    }

  }
}
