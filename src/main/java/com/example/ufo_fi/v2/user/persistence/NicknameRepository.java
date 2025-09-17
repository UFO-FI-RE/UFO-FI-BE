package com.example.ufo_fi.v2.user.persistence;

import com.example.ufo_fi.v2.user.domain.nickname.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {
}
