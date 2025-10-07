package com.stefanini.cycle_authenticate.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    private int status = 401;

    public UserAlreadyExistsException(){
        super("the user already exists in the application");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }

    public UserAlreadyExistsException(String message, int status){
        super(message);
        this.status = status;
    }

    public int getStatusCode() {
        return status;
    }

    public void setStatusCode(int status) {
        this.status = status;
    }
}
