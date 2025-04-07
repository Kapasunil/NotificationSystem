package com.example.com.domain.entity;

import com.example.com.enums.NotificationStatus;
import com.example.com.enums.Severity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "securityAlerts",schema = "gdc-db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemAlerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long alertId;

    @Column(name = "admin_id")
    private Long adminId;;

    @Column(name = "message",nullable = false)
    private String message;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Builder.Default
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status=NotificationStatus.UNREAD;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
