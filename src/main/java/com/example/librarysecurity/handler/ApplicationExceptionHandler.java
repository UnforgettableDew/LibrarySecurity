package com.example.librarysecurity.handler;

import com.example.librarysecurity.exception.*;
import com.example.librarysecurity.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(UsernameNotFoundException exception,
                                                                 HttpServletRequest request) {
        HttpStatus httpStatus = NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                httpStatus,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getRequestURI());

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotValidTokenException.class})
    public ResponseEntity<Object> handleNotValidTokenException(NotValidTokenException exception,
                                                               HttpServletRequest request) {
        HttpStatus httpStatus = BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                httpStatus,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getRequestURI());

        return ResponseEntity
                .status(httpStatus)
                .header("error", exception.getMessage())
                .body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception,
                                                               HttpServletRequest request) {
        HttpStatus httpStatus = BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                httpStatus,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getRequestURI());

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = {InvalidPasswordException.class})
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException exception,
                                                                       HttpServletRequest request) {
        HttpStatus httpStatus = BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(
                exception.getMessage(),
                httpStatus,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getRequestURI());

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}

