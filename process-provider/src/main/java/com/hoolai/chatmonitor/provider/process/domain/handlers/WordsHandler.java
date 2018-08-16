package com.hoolai.chatmonitor.provider.process.domain.handlers;

import com.hoolai.chatmonitor.provider.process.domain.WordsResultEnum;
import com.hoolai.chatmonitor.provider.process.domain.algorithms.IAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WordsHandler implements IWordsHandler {

    @Resource
    private IAlgorithm algorithm;

    @Override
    public WordsResultEnum handle(List<Word> wordList) {
        WordsResultEnum result = WordsResultEnum.LEGAL;
        for (Word wold : wordList) {
            WordsResultEnum resultEnum = algorithm.evaluate(wold);
            if (resultEnum == WordsResultEnum.ILLEGAL) {
                return resultEnum;
            }
            if (resultEnum == WordsResultEnum.SUSPICIOUS) {
                result = WordsResultEnum.SUSPICIOUS;
            }
        }
        return result;
    }
}
