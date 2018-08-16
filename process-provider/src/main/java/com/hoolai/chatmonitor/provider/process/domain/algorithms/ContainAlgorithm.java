package com.hoolai.chatmonitor.provider.process.domain.algorithms;

import com.hoolai.chatmonitor.provider.process.domain.WordsResultEnum;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ContainAlgorithm implements IAlgorithm {

    private Set<String> illegalSet = new HashSet<>(Arrays.asList("a", "非法"));
    private Set<String> suspiciousSet = new HashSet<>(Arrays.asList("b", "可疑"));

    @Override
    public WordsResultEnum evaluate(Word word) {
        if (illegalSet.contains(word.getText())) {
            return WordsResultEnum.ILLEGAL;
        }
        if (suspiciousSet.contains(word.getText())) {
            return WordsResultEnum.SUSPICIOUS;
        }
        return WordsResultEnum.LEGAL;
    }
}
