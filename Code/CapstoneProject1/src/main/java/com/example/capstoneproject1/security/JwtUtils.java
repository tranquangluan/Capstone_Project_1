//package com.example.capstoneproject1.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtils {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private int expiration;
//
//    public String generateToken(String username, String role) {
//        Key key = Keys.hmacShaKeyFor(secret.getBytes());
//
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("role", role)
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public Claims parseToken(String token) {
//        Key key = Keys.hmacShaKeyFor(secret.getBytes());
//
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}