package com.stefanini.cycle_authenticate.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    private int status = 401;

    public UserAlreadyExistsException(){
        super("Usuário existe na aplicação");
    }

    public UserAlreadyExistsException(String message){
        super(message);
    }

    public UserAlreadyExistsException(String message, int status){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
