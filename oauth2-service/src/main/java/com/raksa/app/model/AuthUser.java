package com.raksa.app.model;

import com.raksa.app.enums.AuthProvider;
import com.raksa.app.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_user")
@Data
public class AuthUser {

    @Id
    @Column(name = "id")
    private String id;

    @PrePersist
    private void prePersist() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = IdGenerator.generateUUID("AUTH_ID_");
        }
    }

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(nullable = false, unique = true)
    private String providerId; // Google "sub" claim

    private String role = "USER";

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

}
