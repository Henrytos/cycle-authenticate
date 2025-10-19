package com.stefanini.cycle_authenticate.domain.exceptions;

public class PasswordNotWithinStandards extends RuntimeException{
    public PasswordNotWithinStandards (){
    super("A senha deve conter um caractere especial, maiúsculo ou minúsculo, e no máximo 20 caracteres");
    }
}

