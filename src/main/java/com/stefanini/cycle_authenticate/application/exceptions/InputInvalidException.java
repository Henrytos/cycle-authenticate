package com.stefanini.cycle_authenticate.application.exceptions;

public class InputInvalidException extends RuntimeException{

    public  InputInvalidException(){
        super("Input Invalid Exception");
    }

    public  InputInvalidException(String message){
        super(message);
    }

}
