package com.example.librarysecurity.jwt;

import com.example.librarysecurity.exception.NotValidTokenException;
import com.example.librarysecurity.service.ApplicationUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtTokenProvider {
    private final JwtService jwtService;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    public JwtTokenProvider(JwtService jwtService, ApplicationUserDetailsService applicationUserDetailsService) {
        this.jwtService = jwtService;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    public void provideNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        try {
            String refreshToken = jwtService.checkHeader(authorizationHeader);

            UserDetails user =
                    applicationUserDetailsService
                            .loadUserByUsername(jwtService.getUsername(jwtService.getClaims(refreshToken)));

            String accessToken = provideAccessToken(
                    user.getUsername(),
                    user.getAuthorities(),
                    request.getRequestURI(),
                    jwtService.getSecretKeyForSigning()
            );

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);

            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception exception) {
            throw new NotValidTokenException(exception.getMessage());
        }

    }


    public String provideAccessToken(String username,
                                     Collection<? extends GrantedAuthority> authorities,
                                     String requestURL,
                                     SecretKey secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authority", authorities)
                .setIssuer(requestURL)
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(jwtService.getAccessTokenExpirationDate())
                .signWith(secretKey)
                .compact();
    }
}
