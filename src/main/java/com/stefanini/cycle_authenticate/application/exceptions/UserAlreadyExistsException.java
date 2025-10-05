package com.stefanini.cycle_authenticate.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    private int statusCode = 401;

    public UserAlreadyExistsException(){
        super("the user already exists in the application");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }

    public UserAlreadyExistsException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
