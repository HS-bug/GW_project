package com.hsbug.backend.app.notification;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.hsbug.backend.app.refrigerator.api.ApiController;
import com.hsbug.backend.app.user_register.UserRegisterEntity;
import com.hsbug.backend.app.user_register.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class NotificationsController {

    @Autowired
    FcmService fcmService;

    @Autowired
    UserRegisterService userRegisterService;
    @Autowired
    FcmTokenRepository fcmTokenRepository;

    @Value("${project.properties.firebase-multicast-message-size}")
    Long multicastMessageSize;

    //@Scheduled(cron = "10 * * * * *") 매 분 10초마다 체크
    //@Scheduled(cron = "0 0 9 * * *") 정각 9시마다 체크
    @Scheduled(cron = "0 13 14 * * *")
    @PostMapping(value = "/pushs/users") // 위에 scheduled 되면 이 줄 삭제
    public void notificationUser() throws FirebaseMessagingException{
        List<String> user = userRegisterService.findByRefrigSomething();
        if (user != null){
            for (int i =0; i < user.size(); i++){
                System.out.println(user.get(i));
                String email = user.get(i);
                FcmTokenEntity fcmTokenEntity = fcmTokenRepository.findByEmail(email);

                Notification notification = Notification.builder()
                        .setTitle("유통기한이 다가오는 상품이 있어요!")
                        .setBody("테스트 입니다. "+i)
                        .setImage(null)
                        .build();

                Message.Builder builder = Message.builder();

                System.out.println(fcmTokenEntity.getToken());
                Message msg = builder
                        .setToken(fcmTokenEntity.getToken()) // 이거 풀어서 사용
                        .setNotification(notification)
                        .build();

                fcmService.sendMessage(msg);


            }

        }
    }
}
