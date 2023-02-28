package com.example.librarysecurity.model;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final String timestamp;
    private final String path;

    public ExceptionResponse(String message, HttpStatus httpStatus, String timestamp, String path) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp.toString();
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
