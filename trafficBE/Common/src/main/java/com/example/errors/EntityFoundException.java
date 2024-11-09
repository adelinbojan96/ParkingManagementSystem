package com.example.errors;

public class EntityFoundException extends RuntimeException{

    public EntityFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
