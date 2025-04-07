package com.example.com.dto;

import com.example.com.enums.NotificationType;
import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

@Data
public class NotificationDto {
    @NotNull(message ="User ID is required")
    private UUID userId;

    @NotNull(message="Notification type is required")
    private NotificationType type;

    @NotNull(message="Message cannot be empty")
    private String message;
}
