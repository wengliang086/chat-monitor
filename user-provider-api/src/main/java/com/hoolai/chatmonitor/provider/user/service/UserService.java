package com.hoolai.chatmonitor.provider.user.service;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;

public interface UserService {

    Long validateUser(long gameId, String gameUid) throws HException;

    void freeze(Long uid) throws HException;
}
