package com.example.login.service.email;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.exception.MessagingException;
import com.notificationapi.NotificationApi;
import com.notificationapi.model.EmailOptions;
import com.notificationapi.model.NotificationRequest;
import com.notificationapi.model.SmsOptions;
import com.notificationapi.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Value("${notificationapi.id}")
    private String notificationId;

    @Value("${notificationapi.secret}")
    private String notificationSecret;

    public void sendVerificationMessage(RegisterPersonRequestDto registerPersonRequestDto) {

        String notificationName = "${NOTIFICATION_NAME}";

        try{

        NotificationRequest request = new NotificationRequest(notificationName, createUserNotification(registerPersonRequestDto))
                .setEmail(new EmailOptions()
                        .setSubject("Your verification code")
                        .setHtml("Your verification code is: 123456")
                );
        log.info("Sending notification request...");
        String response = createNotification(notificationId,notificationSecret).send(request);
        log.info("Response: {}", response);}
        catch (MessagingException messagingException){
            log.error("It cant send verification notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }

    public void sendWelcomeMessage(RegisterPersonRequestDto registerPersonRequestDto){
        try {
            NotificationRequest request = new NotificationRequest("login", createUserNotification(registerPersonRequestDto))
                    .setEmail(new EmailOptions()
                            .setSubject("Your verification code")
                            .setHtml("Your verification code is: 123456")
                    );
            log.info("Sending notification request...");
            String response = createNotification(notificationId, notificationSecret).send(request);
            log.info("Response: {}", response);
        }catch (MessagingException messagingException){
            log.error("It cant send welcome notification",messagingException);
            throw new MessagingException("It cant send notification in this moment");
        }
    }

    private User createUserNotification(RegisterPersonRequestDto registerPersonRequestDto){
try {
    return new User(registerPersonRequestDto.getEmail())
            .setEmail(registerPersonRequestDto.getEmail())
            .setNumber(registerPersonRequestDto.getPhone());
}catch (MessagingException messagingException){
    log.error("It cant create user notification",messagingException);
    throw new MessagingException("It cant send notification in this moment");
}
    }

    private NotificationApi createNotification(String notificationId,String notificationSecret){
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
