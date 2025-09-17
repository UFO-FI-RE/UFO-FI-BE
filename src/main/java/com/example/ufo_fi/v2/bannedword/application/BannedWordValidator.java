package com.example.ufo_fi.v2.bannedword.application;

import com.example.ufo_fi.v2.bannedword.domain.filter.BannedWordFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannedWordValidator {

    private final BannedWordFilter bannedWordFilter;

    public void validateBannedWord(String content) {

        bannedWordFilter.filter(content);
    }

}