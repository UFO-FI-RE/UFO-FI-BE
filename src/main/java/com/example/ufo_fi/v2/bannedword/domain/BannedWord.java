package com.example.ufo_fi.v2.bannedword.domain;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.exception.BannedWordErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "banned_words")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannedWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "word", nullable = false)
    private String word;

    private BannedWord(String word) {
        this.word = word;
        validateBannedWord(word);
    }

    static public BannedWord from(String word) {
        return BannedWord.builder()
                .word(word)
                .build();
    }

    private void validateBannedWord(String word) {
        if(word.isEmpty()){
            throw new GlobalException(BannedWordErrorCode.BANNED_WORD_IS_BLANK);
        }
    }
}
