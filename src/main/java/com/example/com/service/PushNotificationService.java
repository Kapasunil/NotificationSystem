package com.example.com.service;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {
    public void sendPushNotification(String deviceToken, String title, String body){
        System.out.println("Sending push Notification to: " +deviceToken);
        System.out.println("Title: "+title);
        System.out.println("Body: "+body);
    }
}
