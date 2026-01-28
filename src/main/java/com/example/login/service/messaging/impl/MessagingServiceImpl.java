package com.example.login.service.messaging.impl;

import com.example.login.config.notification.NotificationConfig;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.MessagingException;
import com.example.login.repository.person.PersonRepository;
import com.example.login.service.messaging.MessagingService;
import com.notificationapi.model.EmailOptions;
import com.notificationapi.model.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    @Value("${notificationapi.name}")
    private String notificationName;

    private final PersonRepository personRepository;
    private final NotificationConfig notificationConfig;
    private final EmailHtml emailHtml;


    @Override
    public void sendVerificationMessage(PersonEntity personEntity, String verificationLink) {
        try{

            String htmlContent=emailHtml.buildVerificationEmailHtml(personEntity.getGivenName(),verificationLink);

            NotificationRequest request = new NotificationRequest(notificationName,notificationConfig.createUserNotification(personEntity))
                    .setEmail(new EmailOptions()
                            .setSubject("Hello "+personEntity.getGivenName()+" Verify your account")
                            .setHtml(htmlContent)
                    );

            log.info("Sending notification request...");
            String response = notificationConfig.createNotification().send(request);
            log.info("Response: {}", response);


        } catch (MessagingException messagingException){
            log.error("It cant send verification notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }

    @Override
    public void sendWelcomeMessage(PersonEntity personEntity) {

        String htmlContent = emailHtml.buildWelcomeEmailHtml(personEntity);

        try {
            NotificationRequest request = new NotificationRequest(notificationName, notificationConfig.createUserNotification(personEntity))
                    .setEmail(new EmailOptions()
                            .setSubject("Verification successful")
                            .setHtml(htmlContent)
                    );
            log.info("Sending notification request...");
            String response = notificationConfig.createNotification().send(request);
            log.info("Response: {}", response);
        }catch (MessagingException messagingException){
            log.error("It cant send welcome notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }

    @Override
    public void sendForgotPasswordVerificationMessage(PersonEntity personEntity, String verificationLink) {

        String htmlContent= emailHtml.buildPasswordResetEmailHtml(personEntity.getGivenName(),verificationLink);
        try {
            NotificationRequest request = new NotificationRequest(notificationName, notificationConfig.createUserNotification(personEntity))
                    .setEmail(new EmailOptions()
                            .setSubject("Forgot password")
                            .setHtml(htmlContent)
                    );
            log.info("Sending notification request...");
            String response = notificationConfig.createNotification().send(request);
            log.info("Response: {}", response);
        }catch (MessagingException messagingException){
            log.error("It cant send welcome notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }


    }


}
