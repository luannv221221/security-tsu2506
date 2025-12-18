package com.ra.security.jwt;

import com.ra.security.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtProvider {
    @Value("${expired}")
    private long EXPIRED_TIME;
    @Value("${secret_key}")
    private String SECRET_KEY;
    private Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    // tao token
    public String generateToken(UserPrinciple userPrinciple) {
        // thoi gian song
        Date dateExpiration = new Date(new Date().getTime() + EXPIRED_TIME);
        return Jwts.builder().setSubject(userPrinciple.getUsername()).
                signWith(SignatureAlgorithm.HS256,SECRET_KEY).
                setExpiration(dateExpiration).
                compact();
    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception exception){
            logger.error(exception.getMessage());
        }
        return false;
    }

    // lay ve thong tin Username  gui len tu client
    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
