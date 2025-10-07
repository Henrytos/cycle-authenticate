package com.stefanini.cycle_authenticate.application.exceptions;

public class UserNotFoundException extends Exception{
    private int status = 404;

    public UserNotFoundException(){
        super("user not found");
    }

    public UserNotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }

    public UserNotFoundException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public UserNotFoundException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
