package com.hoolai.chatmonitor.provider.process.domain.algorithms;

import com.hoolai.chatmonitor.provider.process.domain.WordsResultEnum;
import org.apdplat.word.segmentation.Word;

public interface IAlgorithm {

    WordsResultEnum evaluate(Word word);
}
