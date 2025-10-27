package com.example.ufo_fi.v3.user.persistence;

import com.example.ufo_fi.v3.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
