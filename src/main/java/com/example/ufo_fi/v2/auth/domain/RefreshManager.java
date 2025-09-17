package com.example.ufo_fi.v2.auth.domain;

import com.example.ufo_fi.v2.auth.persistence.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshManager {

    private final RefreshRepository refreshRepository;

    public void delete(Refresh refresh) {
        refreshRepository.delete(refresh);
    }

    public void save(Refresh refresh) {
        refreshRepository.save(refresh);
    }
}
