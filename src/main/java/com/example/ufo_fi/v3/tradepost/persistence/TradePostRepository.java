package com.example.ufo_fi.v3.tradepost.persistence;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long> {
}
