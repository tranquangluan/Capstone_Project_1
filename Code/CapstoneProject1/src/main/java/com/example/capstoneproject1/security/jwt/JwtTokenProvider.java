package com.example.capstoneproject1.security.jwt;


import com.example.capstoneproject1.security.userPrincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private String message;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private static final String JWT_SECRET = "F2C583391C63F2D39E3EE2955677A";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 86400;

    // generated jwt from information of user
    public String generateToken(Authentication authentication ) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setId(userPrinciple.getId().toString())
                .setSubject(userPrinciple.getUsername())
                .claim("roles", roles) // add accessControl in claims
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION * 2000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication ) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setId(userPrinciple.getId().toString())
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    // get information from jwt secret
    public String getUserEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getId();
    }

    public static Collection<GrantedAuthority> getRolesFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        List<String> rolesList = claims.get("roles", List.class);

        String[] roles = rolesList.toArray(new String[rolesList.size()]);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return authorities;
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            setMessage("Validation token is valid");
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
