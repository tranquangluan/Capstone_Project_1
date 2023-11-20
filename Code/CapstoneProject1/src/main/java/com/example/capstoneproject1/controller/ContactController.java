package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.request.ContactForm;
import com.example.capstoneproject1.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    EmailServiceImpl emailServiceImpl;


    @PostMapping(value = "/send-contact", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    } , produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?>  sendContact(ContactForm contactForm) {
        try {
            emailServiceImpl.sendMailCreateCustomer(contactForm);
            return new ResponseEntity<>("Oke", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
