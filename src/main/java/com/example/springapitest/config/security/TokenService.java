package com.example.springapitest.config.security;

import com.example.springapitest.model.User;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        User user = (User) auth.getPrincipal();
        Date today = new Date();
        Date todayPlusExpiration = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API Spring Test")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(todayPlusExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
