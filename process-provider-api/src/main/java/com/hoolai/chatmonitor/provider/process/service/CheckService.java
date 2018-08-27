package com.hoolai.chatmonitor.provider.process.service;

import java.util.List;
import java.util.Map;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;

public interface CheckService {

    void msgCheck(Long uid, long gameId, String gameUid, String msg) throws HException;
    ReturnValue<List<MsgSuspicious>> list(Long uid, long gameId, String msg,List<Long> gameIds) throws HException;
    ReturnValue<MsgSuspicious> msgSure(Long id, String illegalWords,Long opUid) throws HException;
    ReturnValue<List<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious>> selectSuspiciousMapList(String account,
			String gameName, String msg,Byte status, Integer gameId, Integer groupId)throws HException ;

}
