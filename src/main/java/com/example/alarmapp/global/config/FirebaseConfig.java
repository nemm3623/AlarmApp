package com.example.alarmapp.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {

        // firebase-service-key.json 파일을 classpath에서 불러옴
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-key.json").getInputStream());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp app;

        // 이미 초기화된 FirebaseApp이 있으면 가져오고, 없으면 새로 생성
        if (FirebaseApp.getApps().isEmpty()) {
            app = FirebaseApp.initializeApp(options);
        } else {
            app = FirebaseApp.getApps().getFirst();
        }

        return FirebaseMessaging.getInstance(app);
    }
}
