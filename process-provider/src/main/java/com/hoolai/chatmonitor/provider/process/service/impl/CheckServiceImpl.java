package com.hoolai.chatmonitor.provider.process.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
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
    public void msgCheck(Long uid, long gameId, String gameUid, String msg) {
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
                MsgSuspicious t = new MsgSuspicious();
                t.setCreateTime(new Date());
                t.setUid(uid);
                t.setMsg(msg);
                t.setGameId(gameId);
                msgSuspiciousDao.save(t);
                return;
            case ILLEGAL:
                throw new HException(HExceptionEnum.SENSITIVE_WORD_FIND);
        }
    }


	//可疑信息列表
	@Override
	public ReturnValue<List<MsgSuspicious>> list(Long uid, long gameId,
			String msg, List<Long> gameIds) throws HException {
		
		MsgSuspicious property=new MsgSuspicious();
    	property.setUid(uid);
    	property.setGameId(gameId);
    	property.setMsg(msg);
    	List<MsgSuspicious> list=msgSuspiciousDao.list(property,gameIds); 
    	
    	ReturnValue<List<MsgSuspicious>> result=new ReturnValue<List<MsgSuspicious>>();
    	result.setValue(list);
    	
		return result;
	}

	//人工审核可疑信息
	@Override
	public ReturnValue<MsgSuspicious> msgSure(Long id, String illegalWords, Long opUid)
			throws HException {
		
		if(id==null || id==0l){
			throw new HException( new DefaultReturnCode(null,-1,"suspicious_id_is_null") );
		}
		
		MsgSuspicious property=msgSuspiciousDao.get(id);
		if(property==null){
			throw new HException( new DefaultReturnCode(null,-1,"suspicious_not_exist") );
		}
		
		property.setStatus((byte)1);//正常：1
		if(Strings.isNotEmpty(illegalWords)){
			property.setStatus((byte)-1);//illegal：-1
			property.setIllegalWords(illegalWords);
		}
		
		property.setOpUid(opUid);
		
		msgSuspiciousDao.update(property);		
		
		ReturnValue<MsgSuspicious> result=new ReturnValue<MsgSuspicious>();
    	result.setValue(property);
    	
    	return result;
		
	}



}
