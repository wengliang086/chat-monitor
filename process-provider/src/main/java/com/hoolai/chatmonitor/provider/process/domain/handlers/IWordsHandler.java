package com.hoolai.chatmonitor.provider.process.domain.handlers;

import com.hoolai.chatmonitor.provider.process.domain.WordsResultEnum;
import org.apdplat.word.segmentation.Word;

import java.util.List;

public interface IWordsHandler {

    WordsResultEnum handle(List<Word> wordList);
}
