package com.hsbug.backend.app.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j
public class FcmService {


    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    private FirebaseMessaging instance;

    public void sendTargetMessage(String targetToken, String title, String body) throws FirebaseMessagingException {
        this.sendTargetMessage(targetToken, title, body, null);
    }
    public void sendTargetMessage(String targetToken, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setToken(targetToken).setNotification(notification).build();
        sendMessage(msg);
    }
/*
    public void sendTopicMessage(String topic, String title, String body) throws FirebaseMessagingException {
        this.sendTopicMessage(topic, title, body, null);
    }
    public void sendTopicMessage(String topic, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).build();
        sendMessage(msg);
    }*/

    public String sendMessage(Message message) throws FirebaseMessagingException {
        System.out.println("message is " + message);
        return this.instance.send(message);
    }

    @PostConstruct
    public void firebaseSetting() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource("firebase/recipe-refrigerator-firebase-adminsdk-lx8r4-de0017b4cb.json").getInputStream())
                .createScoped((Arrays.asList(fireBaseCreateScoped)));

        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);
    }

    public BatchResponse sendMessage(MulticastMessage message) throws FirebaseMessagingException {
        return this.instance.sendMulticast(message);
    }
}
