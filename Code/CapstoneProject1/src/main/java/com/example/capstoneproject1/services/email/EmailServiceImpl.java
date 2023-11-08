package com.example.capstoneproject1.services.email;


import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailSender {

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) {

    }
}
