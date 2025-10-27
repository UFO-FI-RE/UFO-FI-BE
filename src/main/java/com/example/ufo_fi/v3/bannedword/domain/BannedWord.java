package com.example.ufo_fi.v3.bannedword.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banned_words")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "word")
    private String word;

    @Builder(access = AccessLevel.PRIVATE)
    private BannedWord(String word) {
        this.word = requireWord(word);
    }

    public static BannedWord from(String word) {
        return BannedWord.builder()
                .word(word)
                .build();
    }

    private String requireWord(String word) {
        if(word == null || word.isEmpty()){
            throw new IllegalArgumentException("word는 null일 수 없습니다.");
        }
        return word;
    }
}
