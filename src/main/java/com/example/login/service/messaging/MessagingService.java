package com.example.login.service.messaging;

import com.example.login.entity.PersonEntity;

public interface MessagingService {

    void sendVerificationMessage(PersonEntity personEntity, String verificationLink);

    void sendWelcomeMessage(PersonEntity personEntity);

    void sendForgotPasswordVerificationMessage(PersonEntity personEntity, String verificationLink);

}
