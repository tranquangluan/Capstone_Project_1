package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.request.ContactForm;
import com.example.capstoneproject1.services.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    EmailServiceImpl emailServiceImpl;


    @GetMapping("/send-contact")
    public @ResponseBody ResponseEntity<?>  sendContact(ContactForm contactForm) {



        return null;
    }
}
