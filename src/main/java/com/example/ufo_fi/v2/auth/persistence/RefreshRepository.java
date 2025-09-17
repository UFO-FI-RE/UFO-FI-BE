package com.example.ufo_fi.v2.auth.persistence;

import com.example.ufo_fi.v2.auth.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
}
