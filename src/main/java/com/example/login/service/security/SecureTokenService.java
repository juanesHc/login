package com.example.login.service.security;

import com.example.login.entity.SecureTokenEntity;

public interface SecureTokenService {

    SecureTokenEntity createToken();

    void saveSecureToken(SecureTokenEntity secureToken);

    SecureTokenEntity findByToken(String token);

    void removeToken(SecureTokenEntity secureToken);

}
