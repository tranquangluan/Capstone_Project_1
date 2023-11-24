package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.request.ContactForm;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.UserRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    EmailServiceImpl emailServiceImpl;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    UserRepository userRepository;


    @PostMapping(value = "/send-contact", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    } , produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?>  sendContact(ContactForm contactForm , HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            // user not found
            if( !userOptional.isPresent() )
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            emailServiceImpl.sendMailCreateCustomer(contactForm);
            return new ResponseEntity<>(new ResponseMessage(0, "Send Contact Successful!", 201), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}
