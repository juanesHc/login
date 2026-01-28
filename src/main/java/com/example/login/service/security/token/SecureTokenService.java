package com.example.login.service.security.token;

import com.example.login.entity.SecureTokenEntity;
import com.example.login.entity.enums.TokenTypeEnum;

public interface SecureTokenService {

    SecureTokenEntity createToken(TokenTypeEnum tokenType);

    void saveSecureToken(SecureTokenEntity secureToken);

    void removeToken(SecureTokenEntity secureToken);

    SecureTokenEntity validateToken(String token,TokenTypeEnum tokenType);

}
