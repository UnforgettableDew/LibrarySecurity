package com.example.librarysecurity.jwt.exception_handler;

import com.example.librarysecurity.model.BaseFilterExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exc) throws IOException {
        BaseFilterExceptionResponse.generateFilterExceptionResponse(
                request,
                response,
                HttpStatus.FORBIDDEN,
                exc.getMessage()
        );
    }
}
