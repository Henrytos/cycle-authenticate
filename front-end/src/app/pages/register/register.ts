import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder
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
    const { email, password } = this.registerForm.value;

    if (this.registerForm.valid)
      this.registerForm.reset()

  }
}
