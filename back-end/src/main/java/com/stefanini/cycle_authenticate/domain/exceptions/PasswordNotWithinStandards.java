package com.stefanini.cycle_authenticate.domain.exceptions;

public class PasswordNotWithinStandards extends RuntimeException{
    public PasswordNotWithinStandards (){
    super("password must contain a special character, uppercase or lowercase and a maximum of 20 characters");
    }
}

