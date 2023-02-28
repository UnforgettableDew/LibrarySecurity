package com.example.librarysecurity.exception;

public class NotValidTokenException extends RuntimeException{
    public NotValidTokenException(String message) {
        super(message);
    }
}
