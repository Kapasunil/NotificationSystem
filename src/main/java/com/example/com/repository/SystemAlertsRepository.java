package com.example.com.repository;

import com.example.com.domain.entity.SystemAlerts;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SystemAlertsRepository extends JpaRepository<SystemAlerts, Long> {
    List<SystemAlerts> findByStatus(NotificationStatus status);
    List<SystemAlerts> findBySeverity(Severity severity);
    List<SystemAlerts> findByAdminId(Long adminId);
}
