package com.raksa.app.Repository;

import com.raksa.app.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByProviderId(String username);
    Optional<AuthUser> findByEmail(String email);
}
