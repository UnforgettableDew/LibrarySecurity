package com.example.librarysecurity.handler;

import com.example.librarysecurity.model.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class LibraryExceptionHandler {
    private final FeignExceptionParser feignExceptionParser;

    @Autowired
    public LibraryExceptionHandler(FeignExceptionParser feignExceptionParser) {
        this.feignExceptionParser = feignExceptionParser;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ExceptionResponse response = new ExceptionResponse(
                errors.toString(),
                httpStatus,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getRequestURI());

        return new ResponseEntity<>(response, httpStatus);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {FeignException.class})
    public ResponseEntity<Object> handleFeignException(FeignException exception, HttpServletRequest request) throws JsonProcessingException {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = exception.getMessage();

        Map<String, Object> responseMap = feignExceptionParser.getResponse(exceptionMessage);

        ExceptionResponse response = new ExceptionResponse(
                (String) responseMap.get("message"),
                HttpStatus.valueOf((String) responseMap.get("httpStatus")),
                (String) responseMap.get("timestamp"),
                request.getRequestURI());

        return new ResponseEntity<>(response, httpStatus);
    }
}
