package com.burak.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Handling all exceptions in just one class
@ControllerAdvice
public class RestExceptionHandler {

    //handling EntityNotFoundException
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exc) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    //handling BadRequestException
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadRequestException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //handling EntityAlreadyExistsException
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EntityAlreadyExistsException exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
    //handling all other exceptions
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}


