package com.example.com.repository;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.User;
import com.example.com.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndStatus(User user, NotificationStatus status);
    List<Notification> findByStatus(NotificationStatus status);
}
