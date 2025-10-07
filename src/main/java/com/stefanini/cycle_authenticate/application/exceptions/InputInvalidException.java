package com.stefanini.cycle_authenticate.application.exceptions;

public class InputInvalidException extends RuntimeException{

    private int status = 400;

    public  InputInvalidException(){
        super("Input Invalid Exception");
    }

    public InputInvalidException(int status) {
        this.status = status;
    }

    public InputInvalidException(String message, int status) {
        super(message);
        this.status = status;
    }

    public InputInvalidException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public InputInvalidException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public InputInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public  InputInvalidException(String message){
        super(message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
