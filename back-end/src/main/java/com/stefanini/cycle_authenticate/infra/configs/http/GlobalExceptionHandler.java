package com.stefanini.cycle_authenticate.infra.configs.http;

import com.stefanini.cycle_authenticate.application.exceptions.InputInvalidException;
import com.stefanini.cycle_authenticate.application.exceptions.InternalApplicationException;
import com.stefanini.cycle_authenticate.application.exceptions.UserAlreadyExistsException;
import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.domain.exceptions.EmailNotWithinStandards;
import com.stefanini.cycle_authenticate.domain.exceptions.PasswordNotWithinStandards;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //    SPRING VALIDATION

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerValidationExceptions(MethodArgumentNotValidException e) {

        HashMap<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    //    DOMAIN
    @ExceptionHandler(EmailNotWithinStandards.class)
    public ResponseEntity<ResponseMessageDTO> handlerEmailNotWithinStandards(EmailNotWithinStandards e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(PasswordNotWithinStandards.class)
    public ResponseEntity<ResponseMessageDTO> handlerPasswordNotWithinStandards(PasswordNotWithinStandards e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    //    APPLICATION
    @ExceptionHandler(InputInvalidException.class)
    public ResponseEntity<ResponseMessageDTO> handlerInputInvalidException(InputInvalidException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(new ResponseMessageDTO(e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler(InternalApplicationException.class)
    public ResponseEntity<ResponseMessageDTO> handlerInternalApplicationException(InternalApplicationException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(new ResponseMessageDTO(e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseMessageDTO> handlerUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatusCode())).body(new ResponseMessageDTO(e.getMessage(), e.getStatusCode()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseMessageDTO> handlerUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(new ResponseMessageDTO(e.getMessage(), e.getStatus()));
    }

}


