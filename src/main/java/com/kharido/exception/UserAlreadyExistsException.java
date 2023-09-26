package com.kharido.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String email) {
        super(email + "' already exists.");
    }
}
