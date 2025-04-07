package com.example.com.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to,String subject, String body) {
        System.out.println("Sending Email to: " +to);
        System.out.println("Subject: " +subject);
        System.out.println("Body: " +body);
    }
}
