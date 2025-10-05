package com.stefanini.cycle_authenticate.infra.configs.http;

import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseMessageDTO> handlerUserAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatusCode())).body(new ResponseMessageDTO(e.getMessage(), e.getStatusCode()));
    }

}


