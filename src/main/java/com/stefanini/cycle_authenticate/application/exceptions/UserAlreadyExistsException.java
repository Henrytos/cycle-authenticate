package com.stefanini.cycle_authenticate.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(){
        super("the user already exists in the application");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }

}
