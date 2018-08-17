package com.hoolai.chatmonitor.provider.process.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.process.dao.MsgSuspiciousDao;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import com.hoolai.chatmonitor.provider.process.domain.WordsResultEnum;
import com.hoolai.chatmonitor.provider.process.domain.handlers.IPreHandler;
import com.hoolai.chatmonitor.provider.process.domain.handlers.IWordsHandler;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import org.apache.logging.log4j.util.Strings;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service(timeout = 10000, retries = 0)
public class CheckServiceImpl implements CheckService {

    @Resource
    private IPreHandler preHandler;

    @Resource
    private IWordsHandler wordsHandler;

    @Resource
    private MsgSuspiciousDao msgSuspiciousDao;

    @Override
    public void msgCheck(String msg) {
        if (Strings.isEmpty(msg)) {
            return;
        }
        // 预处理
        msg = preHandler.handle(msg);
        // 分词
        List<Word> wordList = WordSegmenter.seg(msg);
        // 敏感词评估
        WordsResultEnum resultEnum = wordsHandler.handle(wordList);
        switch (resultEnum) {
            case LEGAL:
                // do nothing
                return;
            case SUSPICIOUS:
                // TODO 做记录
                MsgSuspicious t = new MsgSuspicious();
                t.setCreateTime(new Date());
                t.setUid(222L);
                t.setMsg(msg);
                t.setProductId(1122);
                msgSuspiciousDao.save(t);
                return;
            case ILLEGAL:
                throw new HException(HExceptionEnum.SENSITIVE_WORD_FIND);
        }
    }
}
