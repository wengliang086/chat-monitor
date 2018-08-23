package com.hoolai.chatmonitor.provider.process.service;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;

public interface CheckService {

    void msgCheck(Long uid, long gameId, String gameUid, String msg) throws HException;
    void list(Long uid, long gameId, String gameUid, String msg) throws HException;
    void msgSure(Long uid, Byte status,String illegalWords,Long opUid) throws HException;

}
