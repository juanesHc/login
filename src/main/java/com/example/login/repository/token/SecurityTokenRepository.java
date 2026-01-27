package com.example.login.repository.token;

import com.example.login.entity.SecureTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecurityTokenRepository extends JpaRepository<SecureTokenEntity, UUID> {
    SecureTokenEntity findByToken(final String token);
}
