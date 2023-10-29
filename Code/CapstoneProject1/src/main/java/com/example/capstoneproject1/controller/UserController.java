package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.security.userPrincal.UserDetailService;
import com.example.capstoneproject1.security.userPrincal.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserRepository userRepository;






@GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userEmail = jwtTokenProvider.getUserNameFromToken(token);
                UserPrinciple userPrinciple = (UserPrinciple) userDetailService.loadUserByUsername(userEmail);
                return new ResponseEntity<>(userPrinciple, HttpStatus.OK);
            } else
                return new ResponseEntity<>(new ResponseMessage(jwtTokenProvider.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Message: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
