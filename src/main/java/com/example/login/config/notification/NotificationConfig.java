package com.example.login.config.notification;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.MessagingException;
import com.notificationapi.NotificationApi;
import com.notificationapi.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConfig {

    @Value("${notificationapi.id}")
    private String notificationId;

    @Value("${notificationapi.secret}")
    private String notificationSecret;


    public User createUserNotification(PersonEntity personEntity){
        try {
            return new User(personEntity.getEmail())
                    .setEmail(personEntity.getEmail())
                    .setNumber(personEntity.getPhone());
        }catch (MessagingException messagingException){
            log.error("It cant create user notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }

    public NotificationApi createNotification(){
        try {
            return new NotificationApi(
                    notificationId,
                    notificationSecret
            );
        }catch (MessagingException messagingException){
            log.error("It cant create notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }


}
