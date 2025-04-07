package com.example.com.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String phoneNumber,String message) {
        System.out.println("Sending Sms to: " +phoneNumber);
        System.out.println("Message: "+message);
    }
}
