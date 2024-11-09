package com.example;

import com.example.errors.EntityFoundException;
import com.example.errors.EntityNotFoundException;
import com.example.errors.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<String> handleInvalidValueException(InvalidValueException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(EntityFoundException.class)
    public ResponseEntity<String> handleEntityFoundException(EntityFoundException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
