package com.example.capstoneproject1.services.email;

import com.example.capstoneproject1.dto.request.ContactForm;

public interface EmailService {
    void sendMailCreateCustomer(ContactForm contactForm);
}
