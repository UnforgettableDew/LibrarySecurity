package com.example.librarysecurity.jwt;

import com.example.librarysecurity.exception.RefreshTokenNotProvidedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@ConfigurationProperties(prefix = "jwt")
public class JwtService {
    private String secretKey;
    private Integer accessTokenExpirationAfterMinutes;
    private Integer refreshTokenExpirationAfterWeeks;

    public JwtService() {
    }

    public Integer getRefreshTokenExpirationAfterWeeks() {
        return refreshTokenExpirationAfterWeeks;
    }

    public void setRefreshTokenExpirationAfterWeeks(Integer refreshTokenExpirationAfterWeeks) {
        this.refreshTokenExpirationAfterWeeks = refreshTokenExpirationAfterWeeks;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setAccessTokenExpirationAfterMinutes(Integer accessTokenExpirationAfterMinutes) {
        this.accessTokenExpirationAfterMinutes = accessTokenExpirationAfterMinutes;
    }

    public SecretKey getSecretKeyForSigning() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Date getAccessTokenExpirationDate() {
        return new java.util.Date(System.currentTimeMillis() + accessTokenExpirationAfterMinutes * 60 * 1000);
    }

    public Date getRefreshTokenExpirationDate() {
        return new java.util.Date(System.currentTimeMillis() + refreshTokenExpirationAfterWeeks
                * 604800000);
    }

    public Jws<Claims> getClaims(String token){
        return  Jwts.parser()
                .setSigningKey(getSecretKeyForSigning())
                .parseClaimsJws(token);
    }

    public String getUsername(Jws<Claims> claimsJws){
        return claimsJws.getBody().getSubject();
    }

    public String checkHeader(String token){
        if(token == null || !token.startsWith("Bearer "))
            throw new IllegalArgumentException("Header is null or doesn't start with 'Bearer '");
        return token.substring("Bearer".length());
    }

    public void checkIsRefreshToken(String token){
        Jws<Claims> claimsJws = getClaims(token);
        Object isEmpty = claimsJws.getBody().get("authority");
        if(isEmpty == null)
            throw new RefreshTokenNotProvidedException("Provided access token. Refresh token expected");
    }
}

