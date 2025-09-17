package com.example.ufo_fi.v2.user.persistence;

import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUser(User user);

    boolean existsByUser(User user);
}
