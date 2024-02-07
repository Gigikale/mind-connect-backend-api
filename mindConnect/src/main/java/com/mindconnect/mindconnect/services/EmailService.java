package com.mindconnect.mindconnect.services;

public interface EmailService {
    void sendEmail(String message, String subject, String recipient);
}
