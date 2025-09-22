package com.example.ufo_fi.config;

import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.google.firebase.messaging.FirebaseMessaging;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration(proxyBeanMethods = false)
public class TestNotificationConfig {
    @Bean
    public WebPushClient webPushClient() {
        return Mockito.mock(WebPushClient.class);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return Mockito.mock(FirebaseMessaging.class);
    }
}
