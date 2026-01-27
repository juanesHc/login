package com.example.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="secure_tokens")
public class SecureTokenEntity extends BaseEntity {
    @Column(unique=true)
    private String token;

    @Column(updatable = false , nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PersonEntity person;
}
