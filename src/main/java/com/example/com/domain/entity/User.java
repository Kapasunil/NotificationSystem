    package com.example.com.domain.entity;
    import com.example.com.enums.Gender;
    import com.example.com.enums.Role;
    import jakarta.persistence.*;
    import lombok.*;
    import java.sql.Timestamp;
    import java.util.UUID;

    @Entity
    @Table(name = "users", schema = "gdc-db")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "age", nullable = false)
        private Integer age;

        @Enumerated(EnumType.STRING)
        @Column(name = "gender", nullable = false)
        private Gender gender;

        @Column(name = "phone", unique = true, nullable = false)
        private String phone;

        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "is_verified", nullable = false)
        private Boolean isVerified;

        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        private Role role;

        @Column(name = "street")
        private String street;

        @Column(name = "city")
        private String city;

        @Column(name = "state")
        private String state;

        @Column(name = "zip_code")
        private String zipCode;

        @Column(name = "country")
        private String country;

        @Column(name = "created_at", nullable = false, updatable = false)
        private Timestamp createdAt;
    }
