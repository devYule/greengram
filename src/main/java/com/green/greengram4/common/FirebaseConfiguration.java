package com.green.greengram4.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfiguration {
    @Value("${fcm.certification}")
    private String googleApplicationCredentials;

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount =
                    new ClassPathResource(googleApplicationCredentials).getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                log.info("FirebaseApp Initialization Complete !!!");
                log.info("FirebaseApp's class = {}", this.getClass());
                FirebaseApp.initializeApp(options);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
