package com.stefanini.cycle_authenticate.domain.exceptions;

public class EmailNotWithinStandards extends RuntimeException{

    public EmailNotWithinStandards(){
        super("email is invalid");
    }

}
