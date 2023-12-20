package com.example.exception;

public class UserIsBlankException extends RuntimeException {

    public UserIsBlankException(String message){
        super(message);
    }

}