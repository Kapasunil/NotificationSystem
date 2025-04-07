package com.example.com.service;

import com.example.com.domain.entity.Notification;
import com.example.com.dto.NotificationDto;
import com.example.com.enums.Role;
import com.example.com.domain.entity.NotificationLog;
import com.example.com.domain.entity.User;
import com.example.com.domain.entity.UserPreference;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.NotificationType;
import com.example.com.repository.NotificationLogRepository;
import com.example.com.repository.NotificationRepository;
import com.example.com.repository.UserPreferenceRepository;
import com.example.com.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushNotificationService;

    private static final Logger logger= LoggerFactory.getLogger(NotificationService.class);

    @Transactional
    public Notification createAndSendNotification(User user, NotificationType type, String message) {
        Notification notification=Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .status(NotificationStatus.UNREAD)
                .build();
        return saveAndSendNotification(notification);
    }

    @Transactional
    public Notification saveAndSendNotification(Notification notification) {
        notification.setStatus(NotificationStatus.UNREAD);
        Notification savedNotification = notificationRepository.save(notification);

        Optional<UserPreference> preferenceOpt = userPreferenceRepository.findByUserId(notification.getUser().getId());

        String responseMessage = "Failed to send notifications";
        boolean sent = false;
        NotificationType channel=notification.getType();

        if (preferenceOpt.isPresent()) {
            UserPreference pref=preferenceOpt.get();

            try {
                switch (channel) {
                    case EMAIL:
                        if (pref.isEmailEnabled()) {
                            emailService.sendEmail(notification.getUser().getEmail(),
                                    "Notification",
                                    notification.getMessage());
                            responseMessage = "Email sent successfully";
                            sent = true;
                        }
                        break;
                    case SMS:
                        if (pref.isSmsEnabled()) {
                            smsService.sendSms(notification.getUser().getPhone(),
                                    notification.getMessage());
                            responseMessage = "Sms Sent successfully";
                            sent = true;
                        }
                        break;
                    case PUSH:
                        if (pref.isPushEnabled()) {
                            pushNotificationService.sendPushNotification(notification.getUser().getPhone(),
                                    "Notification",
                                    notification.getMessage());
                            responseMessage = "Push notification sent successfully";
                            sent = true;
                        }
                        break;
                }
            } catch (Exception e) {
                responseMessage = "Failed to send "+channel+" : "+e.getMessage();
                logger.error("Error sending notification", e);
            }
        } else {
            logger.warn("UserPreference not found for user: {}", notification.getUser().getId());
            responseMessage="User preferences not found. Notification not sent.";
        }

        NotificationLog log = NotificationLog.builder()
                .notification(savedNotification)
                .channel(channel)
                .status(sent ? NotificationStatus.SENT : NotificationStatus.UNREAD)
                .response(responseMessage)
                .build();
        notificationLogRepository.save(log);

        if (sent) {
            savedNotification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(savedNotification);
        }
        return savedNotification;
    }

    public List<Notification> getUserNotifications(UUID userId) {
        return userRepository.findById(userId)
                .map(notificationRepository::findByUser)
                .orElseGet(List::of);
    }

    public List<Notification> getUnreadUserNotifications(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> notificationRepository.findByUserAndStatus(user,NotificationStatus.UNREAD))
                .orElseGet(List::of);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setStatus(NotificationStatus.READ);
            notificationRepository.save(notification);
        });
    }

    public List<NotificationLog> getNotificationLogs(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notificationLogRepository::findByNotification)
        .orElseGet(List::of);
    }

    @Transactional
    public void notifyAllUsers(NotificationType type, String message) {
        List<User> allUsers= userRepository.findAll();
        allUsers.forEach(user->createAndSendNotification(user,type,message));
    }

    @Transactional
    public void notifyAdmins(NotificationType type, String message) {
        List<User> admins=userRepository.findByRole(Role.ADMIN);
        admins.forEach(admin->createAndSendNotification(admin,type,message));
    }
}


