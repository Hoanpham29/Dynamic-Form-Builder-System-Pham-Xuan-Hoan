package com.project.DynamicFormBuilderSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleResponseStatus(ResponseStatusException ex){
        return buildResponseEntity(ex, HttpStatus.valueOf(ex.getStatusCode().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleException(Exception ex){
        return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionResponses> buildResponseEntity(Exception ex, HttpStatus status){
        ExceptionResponses error = new ExceptionResponses();
        error.setStatus(status.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, status);
    }
}
