package com.example.ufo_fi.v2.user.persistence;

import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePhotoRepository extends JpaRepository<ProfilePhoto, Long> {
}
