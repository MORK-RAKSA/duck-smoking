package com.raksa.app.model;

import com.raksa.app.enums.AuthProvider;
import com.raksa.app.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private String provider;
    private String providerId;
    private String name;
    private String email;
    private String picture;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
