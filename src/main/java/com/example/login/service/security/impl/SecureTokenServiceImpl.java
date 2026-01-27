package com.example.login.service.security.impl;

import com.example.login.entity.SecureTokenEntity;
import com.example.login.repository.token.SecurityTokenRepository;
import com.example.login.service.security.SecureTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SecureTokenServiceImpl implements SecureTokenService {

    private static BytesKeyGenerator DEFAULT_TOKEN_GENERATOR= KeyGenerators.secureRandom(12);

    @Value("${token.validity}")
    private long tokenValidityInSeconds;

    private final SecurityTokenRepository securityTokenRepository;

    @Override
    public SecureTokenEntity createToken() {
        String tokenValue=new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
        SecureTokenEntity secureTokenEntity=new SecureTokenEntity();
        secureTokenEntity.setToken(tokenValue);
        secureTokenEntity.setExpiredAt(LocalDateTime.now().plusSeconds(tokenValidityInSeconds));

        return secureTokenEntity;
    }

    @Override
    public void saveSecureToken(SecureTokenEntity secureToken) {
        securityTokenRepository.save(secureToken);
    }

    @Override
    public SecureTokenEntity findByToken(String token) {
        return securityTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(SecureTokenEntity secureToken) {
        securityTokenRepository.delete(secureToken);
    }
}
