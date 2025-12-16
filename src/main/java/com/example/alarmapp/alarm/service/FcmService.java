package com.example.alarmapp.alarm.service;

import com.example.alarmapp.member.domain.Member;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendAlarmPush(Member member, Long alarmId, String title, int snoozeMinutes) {

        if (member.getFcmToken() == null) {
            System.out.println("❌ FCM 토큰 없음 → PUSH 불가");
            return;
        }

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(
                        AndroidNotification.builder()
                                .setChannelId("alarm_channel")
                                .setTitle(title)
                                .setBody("⏰ 알람 시간이 되었습니다!")
                                .setSound("default")
                                .build()
                )
                .build();

        Message message = Message.builder()
                .setToken(member.getFcmToken())
                .putData("alarmId", alarmId.toString())
                .putData("title", title)
                .putData("snoozeMinutes", String.valueOf(snoozeMinutes))
                .setAndroidConfig(androidConfig)
                .build();

        try {
            firebaseMessaging.send(message);
            System.out.println("📨 FCM 푸시 전송 성공");
        } catch (Exception e) {
            System.out.println("❌ 푸시 실패: " + e.getMessage());
        }
    }
}
