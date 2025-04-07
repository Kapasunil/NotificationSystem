package com.example.com.service;

import com.example.com.domain.entity.User;
import com.example.com.enums.NotificationType;
import com.example.com.enums.Role;
import com.example.com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void notifyAdmins(String message, NotificationType notificationType) {
        List<User> admins = userRepository.findByRole(Role.ADMIN);
        admins.forEach(admin->
                notificationService.createAndSendNotification(admin,notificationType,message));
    }

    public void notifyAllUsers(String message, NotificationType notificationType) {
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user ->
                notificationService.createAndSendNotification(user,notificationType,message));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found with Id:"+userId));
    }
}
