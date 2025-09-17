package com.example.ufo_fi.config;

import com.google.firebase.messaging.FirebaseMessaging;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test시 등록되는 firebaseConfig
 * 1. 가짜 Bean으로 등록
 */
@TestConfiguration
public class TestFirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return Mockito.mock(FirebaseMessaging.class);
    }
}
