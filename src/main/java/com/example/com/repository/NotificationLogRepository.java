package com.example.com.repository;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.NotificationLog;
import com.example.com.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {
    List<NotificationLog> findByNotification(Notification notification);
    List<NotificationLog> findByStatus(NotificationStatus status);
    List<NotificationLog> findByNotificationAndStatus(Notification notification,NotificationStatus status);
}
