package com.stefanini.cycle_authenticate.application.exceptions;

public class InternalApplicationException extends RuntimeException {
    private int status = 500;

    public InternalApplicationException(){
        super("internal server error");
    }

    public InternalApplicationException(int status) {
        this.status = status;
    }

    public InternalApplicationException(String message, int status) {
        super(message);
        this.status = status;
    }

    public InternalApplicationException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public InternalApplicationException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

    public InternalApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status) {
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
