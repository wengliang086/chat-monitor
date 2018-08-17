package com.hoolai.chatmonitor.provider.user.service;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;

public interface UserService {

    void validateUser(Long uid) throws HException;

    void freeze(Long uid) throws HException;
}
