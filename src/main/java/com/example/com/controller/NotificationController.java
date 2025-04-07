package com.example.com.controller;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.NotificationLog;
import com.example.com.domain.entity.User;
import com.example.com.dto.NotificationDto;
import com.example.com.enums.NotificationType;
import com.example.com.service.NotificationService;
import com.example.com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody NotificationDto dto) {
        User user=userService.getUserById(dto.getUserId());
        if (user==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(notificationService.createAndSendNotification(
                user,
                dto.getType(),
                dto.getMessage()
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadUserNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUnreadUserNotifications(userId));
    }

    @PutMapping("/{notificationId}/mark-read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{notificationId}/logs")
    public ResponseEntity<List<NotificationLog>> getNotificationLogs(@PathVariable UUID notificationId) {
        return ResponseEntity.ok(notificationService.getNotificationLogs(notificationId));
    }

    @PostMapping("/notify-all")
    public ResponseEntity<Void> notifyAllUsers(
            @RequestBody NotificationType type,
            @RequestBody String message) {
        notificationService.notifyAllUsers(type, message);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/notify-admins")
    public ResponseEntity<Void> notifyAdmins(
            @RequestBody NotificationType type,
            @RequestBody String message) {
        notificationService.notifyAdmins(type, message);
        return ResponseEntity.accepted().build();
    }
}
