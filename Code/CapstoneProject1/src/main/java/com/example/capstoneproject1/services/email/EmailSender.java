package com.example.capstoneproject1.services.email;

public interface EmailSender {
    void sendHtmlMessage(String to, String subject, String htmlBody);
}
