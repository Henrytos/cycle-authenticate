import { Component } from '@angular/core';
import { Logo } from '../../components/logo/logo';
import { Title } from '../../components/title/title';
import { RouterOutlet, RouterLinkWithHref, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { toast } from 'ngx-sonner';

@Component({
  selector: 'app-forgot-password',
  imports: [Logo, Title, ReactiveFormsModule, RouterOutlet, RouterLinkWithHref],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.scss'
})
export class ForgotPassword {

  forgotPasswordForm: FormGroup;
  changePasswordForm: FormGroup;


  isChangePassword: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute
  ){
    this.forgotPasswordForm = formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });

    this.changePasswordForm = formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
  });


    
    route.queryParams.subscribe(params => {
      console.log(params)
      const { token } = params;

      console.log('Token:', token);

      if(token){
        this.isChangePassword = true;
      }

    })
  }


  public changePasswordFormSubmit(){
    if(this.changePasswordForm.valid){
      const password = this.changePasswordForm.get('password')?.value;
      const confirmPassword = this.changePasswordForm.get('confirmPassword')?.value;
      if(password === confirmPassword){
        toast.success('Senha alterada com sucesso!');
      }else{
        toast.error('As senhas não coincidem. Por favor, tente novamente.');
      }
    } else {
      toast.warning('Por favor preencha o formulário corretamente');
    }


  }

  public forgotPasswordFormSubmit(): void {
    if (this.forgotPasswordForm.valid) {
      const email = this.forgotPasswordForm.get('email')?.value;

        const visibleElement = document.querySelector('.visible');

        if (visibleElement) {

          const element = document.querySelector('.visible-info')
          visibleElement.innerHTML = '';

          if(element){
           element.innerHTML = `
            Verifique se recebeu uma mensagem nossa em sua caixa de entrada.

            Não recebeu a mensagem?
            <br/>
            <br/>
            <ul>
              <li class="text">Certifique-se de ter inserido o endereço de email correto de sua conta.</li>
              <li class="text">Se tiver enviado diversas solicitações, aguarde alguns instantes e tente novamente.</li>
              <li class="text">Crianças devem pedir para o gerente da família verificar ou redefinir suas configurações de início de sessão.</li>
            </ul>
          `
          }

         
        }

      toast.success(`Instruções para redefinição de senha foram enviadas para ${email}`);
    } else{
      toast.warning('Por favor preencha o formulário corretamente');
    }
  }
}
