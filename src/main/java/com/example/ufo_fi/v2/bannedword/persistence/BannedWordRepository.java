package com.example.ufo_fi.v2.bannedword.persistence;


import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedWordRepository extends JpaRepository<BannedWord, Long> {

    boolean existsByWord(String word);
}

