package com.example.librarysecurity.jwt;

import com.example.librarysecurity.model.BaseFilterExceptionResponse;
import com.example.librarysecurity.service.ApplicationUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class JwtVerifierToken extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    public JwtVerifierToken(JwtService jwtService, ApplicationUserDetailsService applicationUserDetailsService) {
        this.jwtService = jwtService;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login") ||
                request.getServletPath().equals("/api/v1/security/token/refresh") ||
                request.getServletPath().equals("/api/v1/security/user/register")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authorizationHeader = request.getHeader(AUTHORIZATION);

                String token = jwtService.checkHeader(authorizationHeader);

                jwtService.checkIsRefreshToken(token);
                UserDetails applicationUser =
                        applicationUserDetailsService
                                .loadUserByUsername(jwtService.getUsername(jwtService.getClaims(token)));

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(),
                        null,
                        applicationUser.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                BaseFilterExceptionResponse.generateFilterExceptionResponse(
                        request,
                        response,
                        BAD_REQUEST,
                        exception.getMessage()
                );
            }
        }
    }
}
