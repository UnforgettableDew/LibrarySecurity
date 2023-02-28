package com.example.librarysecurity.jwt.exception_handler;

import com.example.librarysecurity.model.BaseFilterExceptionResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        BaseFilterExceptionResponse.generateFilterExceptionResponse(
                request,
                response,
                UNAUTHORIZED,
                "Wrong username or password"
        );
    }
}
