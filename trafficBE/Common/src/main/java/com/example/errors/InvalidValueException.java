package com.example.errors;

public class InvalidValueException extends RuntimeException{
    public InvalidValueException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
