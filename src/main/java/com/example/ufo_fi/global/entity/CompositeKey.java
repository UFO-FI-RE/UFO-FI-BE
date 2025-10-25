package com.example.ufo_fi.global.entity;

import com.example.ufo_fi.v3.follow.domain.FollowId;
import org.springframework.data.domain.Persistable;

public interface CompositeKey extends Persistable<FollowId> {

    void markNotNew();
}
