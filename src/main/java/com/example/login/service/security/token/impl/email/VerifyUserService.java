package com.example.login.service.security.token.impl.email;

import com.example.login.entity.PersonEntity;
import com.example.login.entity.SecureTokenEntity;
import com.example.login.exception.VerifyUserException;
import com.example.login.repository.person.PersonRepository;
import com.example.login.service.messaging.impl.MessagingServiceImpl;
import com.example.login.service.security.token.impl.SecureTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyUserService {

    @Value("${app.base-url}")
    private String appBaseUrl;

    private final MessagingServiceImpl emailService;
    private final SecureTokenServiceImpl secureTokenService;
    private final PersonRepository personRepository;

    @Transactional
    public void verifyUser(String tokenValue) throws Exception {
        SecureTokenEntity secureToken = secureTokenService.findByToken(tokenValue);

        if (secureToken == null || secureToken.getExpiredAt().isBefore(java.time.LocalDateTime.now())) {
            log.error("Token has expiate or it is invalid");
            throw new VerifyUserException("Token has expiate or it is invalid");
        }

        log.warn("Found no Person with id:",secureToken.getPerson().getId());
        PersonEntity personEntity = personRepository.findById(secureToken.getPerson().getId())
                .orElseThrow(() -> new Exception("Found no Person with id"));

        if (personEntity.isAccountVerified()) {
            log.info("User is already verify");
            secureTokenService.removeToken(secureToken);
            throw new VerifyUserException("the account is already verify");
        }

        personEntity.setAccountVerified(true);
        personRepository.save(personEntity);

        log.info("User verify in successful way: {}", personEntity.getEmail());
        secureTokenService.removeToken(secureToken);

        try {

            emailService.sendWelcomeMessage(personEntity);
            log.info("Welcome Message sent");
        } catch (Exception e) {
            log.warn("it cant send welcome message: {}", e.getMessage());
        }

    }

    public void sendRegistrationConfirmationEmail(PersonEntity personEntity){
        SecureTokenEntity token = secureTokenService.createToken();
        token.setPerson(personEntity);
        secureTokenService.saveSecureToken(token);
        String verificationUrl = appBaseUrl + "/auth/verify?token=" + token.getToken();
        emailService.sendVerificationMessage(personEntity,verificationUrl);
    }

    public void resendRegistrationConfirmationEmail(String email){
        PersonEntity userEntity= personRepository.findByEmail(email);
        if (userEntity == null) {
            throw new VerifyUserException("Usuario no encontrado con el email: " + email);
        }

        if (userEntity.isAccountVerified()) {
            throw new VerifyUserException("Esta cuenta ya está verificada");
        }

        sendRegistrationConfirmationEmail(userEntity);
    }
}
