package com.stefanini.cycle_authenticate.application.exceptions;

public class UnauthorizedException extends RuntimeException{
    private int status = 403;

    public UnauthorizedException(){
        super("usuario não autorizado");
    }

    public UnauthorizedException(int status) {
        this.status = status;
    }

    public UnauthorizedException(String message, int status) {
        super(message);
        this.status = status;
    }

    public UnauthorizedException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public UnauthorizedException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
