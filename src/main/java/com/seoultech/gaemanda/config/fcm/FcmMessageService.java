package com.seoultech.gaemanda.config.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmMessageService {

    private final FirebaseMessaging firebaseMessaging;

    public FcmMessageService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendMessage(Note note) {

        Notification notification = Notification.builder()
                .setTitle(null)
                .setBody(note.getContent())
                .build();

        Message message = Message.builder()
                .setToken(note.getDeviceToken())
                .putAllData(note.getData())
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

}