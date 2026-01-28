package com.example.login.service.security.token.impl;

import com.example.login.entity.SecureTokenEntity;
import com.example.login.entity.enums.TokenTypeEnum;
import com.example.login.exception.InvalidTokenTypeException;
import com.example.login.exception.TokenExpiredException;
import com.example.login.exception.TokenNotFoundException;
import com.example.login.repository.token.SecurityTokenRepository;
import com.example.login.service.security.token.SecureTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecureTokenServiceImpl implements SecureTokenService {

    private static final BytesKeyGenerator TOKEN_GENERATOR = KeyGenerators.secureRandom(12);
    private final SecurityTokenRepository securityTokenRepository;

    @Value("${token.email.validity}")
    private long tokenEmailValidityInSeconds;

    @Value("${token.password.validity}")
    private long tokenPasswordValidityInSeconds;

    @Override
    public SecureTokenEntity createToken(TokenTypeEnum tokenType) {

        SecureTokenEntity secureTokenEntity=new SecureTokenEntity();
        secureTokenEntity.setToken(generateTokenValue());
        secureTokenEntity.setType((tokenType));
        secureTokenEntity.setExpiredAt(resolveExpiration(tokenType));

        return secureTokenEntity;
    }

    @Override
    public void saveSecureToken(SecureTokenEntity secureToken) {
        securityTokenRepository.save(secureToken);
    }

    @Override
    public void removeToken(SecureTokenEntity secureToken) {
        securityTokenRepository.delete(secureToken);
    }

    @Override
    public SecureTokenEntity validateToken(String tokenValue ,TokenTypeEnum tokenType) {
        if (tokenValue == null || tokenValue.isBlank()) {
            throw new IllegalArgumentException("Token value must not be null or empty");
        }

        SecureTokenEntity token = securityTokenRepository.findByToken(tokenValue);

        if (token == null) {
            throw new TokenNotFoundException("Token not found");
        }

        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token has expired");
        }

        if (token.getType() != tokenType) {
            throw new InvalidTokenTypeException("Token type mismatch");
        }

        return token;
    }

    private LocalDateTime resolveExpiration(TokenTypeEnum tokenType) {
        return switch (tokenType) {
            case VERIFY_EMAIL ->
                    LocalDateTime.now().plusSeconds(tokenEmailValidityInSeconds);
            case PASSWORD_RESET ->
                    LocalDateTime.now().plusSeconds(tokenPasswordValidityInSeconds);
        };
    }

    private String generateTokenValue() {
        return new String(
                Base64.encodeBase64URLSafe(TOKEN_GENERATOR.generateKey()),
                StandardCharsets.UTF_8
        );
    }
}
