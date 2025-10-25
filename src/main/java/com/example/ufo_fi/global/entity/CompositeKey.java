package com.example.ufo_fi.global.entity;

import com.example.ufo_fi.v3.follow.domain.FollowId;
import org.springframework.data.domain.Persistable;

public interface CompositeKey<T> extends Persistable<T> {

    void markNotNew();
}
