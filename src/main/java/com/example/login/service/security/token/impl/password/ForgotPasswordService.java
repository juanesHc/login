package com.example.login.service.security.token.impl.password;

import com.example.login.entity.PersonEntity;
import com.example.login.entity.SecureTokenEntity;
import com.example.login.entity.enums.TokenTypeEnum;
import com.example.login.exception.VerifyUserException;
import com.example.login.repository.person.PersonRepository;
import com.example.login.service.messaging.impl.MessagingServiceImpl;
import com.example.login.service.security.token.impl.SecureTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    @Value("${app.base-url}")
    private String appBaseUrl;

    private final PasswordEncoder passwordEncoder;
    private final MessagingServiceImpl emailService;
    private final SecureTokenServiceImpl secureTokenService;
    private final PersonRepository personRepository;

    public void requestPasswordReset(String email) {
        PersonEntity person = personRepository.findByEmail(email);

        if (person == null) {
            throw new VerifyUserException("User not found with email " + email);
        }

        SecureTokenEntity token =
                secureTokenService.createToken(TokenTypeEnum.PASSWORD_RESET);

        token.setPerson(person);
        secureTokenService.saveSecureToken(token);

        String resetUrl = appBaseUrl + "/auth/reset-password?token=" + token.getToken();

        emailService.sendForgotPasswordVerificationMessage(person, resetUrl);

        log.info("Password reset email sent to {}", email);
    }

    public void resetPassword(String tokenValue, String newPassword) {

        SecureTokenEntity token =
                secureTokenService.validateToken(tokenValue, TokenTypeEnum.PASSWORD_RESET);

        PersonEntity person = token.getPerson();

        person.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(person);

        secureTokenService.removeToken(token);

        log.info("Password successfully reset for user {}", person.getEmail());
    }


}
