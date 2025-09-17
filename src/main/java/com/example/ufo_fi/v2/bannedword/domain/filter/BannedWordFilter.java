package com.example.ufo_fi.v2.bannedword.domain.filter;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import com.example.ufo_fi.v2.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.v2.bannedword.persistence.BannedWordRepository;
import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannedWordFilter {

    private final BannedWordRepository bannedWordRepository;
    private final FuzzyMatcher fuzzyMatcher;
    private final Preprocessor preprocessor = new Preprocessor();

    private Trie trie;
    private Trie jamoTrie;

    @PostConstruct
    public void init() {
        reload();
    }

    public void reload() {
        List<String> banWordList = loadBanWords();
        buildTries(banWordList);
    }

    public void filter(String input) {
        if (input == null || input.isBlank()) {
            return;
        }

        String normalized = preprocessor.normalize(input);
        String compacted = normalized.replaceAll("\\s+", "");

        Collection<String> trieMatches = trie.parseText(compacted)
            .stream()
            .map(emit -> emit.getKeyword())
            .collect(Collectors.toSet());
        if (!trieMatches.isEmpty()) {
            throw new GlobalException(BannedWordErrorCode.BANNED_WORD_INCLUDED);
        }

        String jamoText = JamoUtil.toJamo(compacted);
        Collection<String> jamoMatches = jamoTrie.parseText(jamoText)
            .stream()
            .map(emit -> emit.getKeyword())
            .collect(Collectors.toSet());
        if (!jamoMatches.isEmpty()) {
            throw new GlobalException(BannedWordErrorCode.BANNED_WORD_INCLUDED);
        }

        if (fuzzyMatcher.isSuspicious(normalized)) {
            throw new GlobalException(BannedWordErrorCode.BANNED_WORD_INCLUDED);
        }
    }

    private List<String> loadBanWords() {
        return bannedWordRepository.findAll()
            .stream()
            .map(BannedWord::getWord)
            .collect(Collectors.toList());
    }

    private void buildTries(List<String> banWordList) {

        Trie.TrieBuilder trieBuilder = Trie.builder()
            .ignoreCase()
            .removeOverlaps();

        Trie.TrieBuilder jamoTrieBuilder = Trie.builder()
            .ignoreCase()
            .removeOverlaps();

        for (String word : banWordList) {
            trieBuilder.addKeyword(word);
            jamoTrieBuilder.addKeyword(JamoUtil.toJamo(word));
        }

        this.trie = trieBuilder.build();
        this.jamoTrie = jamoTrieBuilder.build();
    }
}