package com.example.ufo_fi.v3.follow.application;

import com.example.ufo_fi.v3.follow.persistence.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private FollowRepository followRepository;
}
