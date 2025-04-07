package com.example.com.service;

import com.example.com.domain.entity.NotificationLog;
import com.example.com.enums.NotificationStatus;
import com.example.com.repository.NotificationLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationRetryService {
    private final NotificationLogRepository notificationLogRepository;
    private  final  NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void retryFailedNotifications() {
        List<NotificationLog> failedLogs=notificationLogRepository.findByStatus(NotificationStatus.UNREAD);
        failedLogs.forEach(log -> {
            if (log.getNotification() !=null) {
                notificationService.saveAndSendNotification(log.getNotification());
                log.setStatus(NotificationStatus.SENT);
                notificationLogRepository.save(log);
            }
        });
    }
}
