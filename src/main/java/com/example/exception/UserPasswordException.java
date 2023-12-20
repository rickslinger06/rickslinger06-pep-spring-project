package com.example.exception;

public class UserPasswordException extends RuntimeException {

    public UserPasswordException(String message){
        super(message);
    }

}