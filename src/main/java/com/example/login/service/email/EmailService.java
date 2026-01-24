package com.example.login.service.email;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
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

        NotificationRequest request = new NotificationRequest(notificationName, createUserNotification(registerPersonRequestDto))
                .setEmail(new EmailOptions()
                        .setSubject("Your verification code")
                        .setHtml("Your verification code is: 123456")
                );
        log.info("Sending notification request...");
        String response = createNotification(notificationId,notificationSecret).send(request);
        log.info("Response: {}", response);
    }

    public void sendWelcomeMessage(RegisterPersonRequestDto registerPersonRequestDto){
        NotificationRequest request = new NotificationRequest("login", createUserNotification(registerPersonRequestDto))
                .setEmail(new EmailOptions()
                        .setSubject("Your verification code")
                        .setHtml("Your verification code is: 123456")
                );
        log.info("Sending notification request...");
        String response = createNotification(notificationId,notificationSecret).send(request);
        log.info("Response: {}", response);
    }

    private User createUserNotification(RegisterPersonRequestDto registerPersonRequestDto){

        return new User(registerPersonRequestDto.getEmail())
                .setEmail(registerPersonRequestDto.getEmail())
                .setNumber(registerPersonRequestDto.getPhone());
    }

    private NotificationApi createNotification(String notificationId,String notificationSecret){
        return new NotificationApi(
                notificationId,
                notificationSecret
        );
    }


}
