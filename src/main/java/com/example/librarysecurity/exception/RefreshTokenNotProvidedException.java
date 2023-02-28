package com.example.librarysecurity.exception;

public class RefreshTokenNotProvidedException extends RuntimeException{
    public RefreshTokenNotProvidedException(String message) {
        super(message);
    }
}
