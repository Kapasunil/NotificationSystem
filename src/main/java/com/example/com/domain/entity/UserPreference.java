package com.example.com.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_preferences",schema = "gdc-db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id",nullable = false,unique = true)
    private UUID userId;

    @Builder.Default
    @Column(nullable = false)
    private boolean emailEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean smsEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean pushEnabled = true;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
