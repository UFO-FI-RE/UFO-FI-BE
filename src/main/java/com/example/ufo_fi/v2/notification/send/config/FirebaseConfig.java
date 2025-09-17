package com.example.ufo_fi.v2.notification.send.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;

// TODO: 리팩토링 시 InfraLayer로 이동

/**
 * Firebase 설정 클래스
 * - Firebase와 연동하기 위한 초기화 작업을 수행하며, Firebase 관련 Bean(FirebaseApp, FirebaseMessaging)을 등록함
 * - test 환경에서는 로딩되지 않도록 @Profile 적용
 */
@Profile("!test")
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config-path:}")
    private String firebaseConfigPath;

    /**
     * FirebaseApp Bean 등록 메서드
     * 1. FirebaseApp은 서비스를 사용할 수 있게 해주는 객체임
     * 2. firebaseConfigPath에 정의된 비밀 키 파일을 읽어 FirebaseApp을 초기화
     * 3. 이미 초기화된 FirebaseApp 인스턴스가 존재하면 해당 인스턴스를 반환
     */
    @Bean
    public FirebaseApp firebaseApp() {

        try (InputStream serviceAccount = resolveFirebaseKeyStream(firebaseConfigPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("ufo-fi")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);
                return app;
            } else {
                return FirebaseApp.getInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * FirebaseMessaging Bean 등록 메서드
     * 1. FCM 서비스를 사용할 수 있게 해주는 FCM 객체
     * 2. firebaseApp 인스턴스를 받아  firebaseMassaging 인스턴스로 반환
     */
    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }


    /**
     * 파일 경로 분기 메서드
     */
    private InputStream resolveFirebaseKeyStream(String path) throws Exception {
        if (path.startsWith("file:")) {
            return new FileInputStream(path.replace("file:", "").trim());
        }
        return new ClassPathResource(path.trim()).getInputStream();
    }
}
