package com.stefanini.cycle_authenticate.application.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("user not found");
    }

    public UserNotFoundException(String message){
        super(message);
    }

}
