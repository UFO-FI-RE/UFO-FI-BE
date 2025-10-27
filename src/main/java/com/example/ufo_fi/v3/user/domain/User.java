package com.example.ufo_fi.v3.user.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import com.example.ufo_fi.v2.user.domain.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
