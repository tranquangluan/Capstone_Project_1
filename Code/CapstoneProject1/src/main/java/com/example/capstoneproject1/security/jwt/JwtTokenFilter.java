package com.example.capstoneproject1.security.jwt;

import com.example.capstoneproject1.security.userPrincal.UserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth") || request.getServletPath().contains("/api/category") || request.getServletPath().contains("/api/spaces/list-spaces") || request.getServletPath().contains("/api/spaces/list-sharing")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // handle check have token
            if(request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Map<String, String> errorMessage = new HashMap<>();
                errorMessage.put("error", String.valueOf(1));
                errorMessage.put("message","Required authentication or authorization");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
            }
            // handle validation token
            String token = getJwtFromRequest(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(userEmail);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    Map<String, String> errorMessage = new HashMap<>();
                    errorMessage.put("error", String.valueOf(1));
                    errorMessage.put("message","User not found");
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, String> errorMessage = new HashMap<>();
                errorMessage.put("error", String.valueOf(1));
                errorMessage.put("message", jwtTokenProvider.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
            }
        } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Map<String, String> errorMessage = new HashMap<>();
                errorMessage.put("error", String.valueOf(1));
                errorMessage.put("message", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
        }
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Check headers authorization have token
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
