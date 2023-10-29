package com.example.capstoneproject1.security.jwt;



import com.example.capstoneproject1.security.userPrincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private String message;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lodaaaaaa";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 86400;

    // Tạo ra jwt từ thông tin user
    public String generateToken(Authentication authentication ) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return  Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION*1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    // get information from jwt secret
    public String getUserNameFromToken(String token) {
       String userEmail = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();

        return userEmail;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            setMessage("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            setMessage("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            setMessage("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            setMessage("JWT claims string is empty.");
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
