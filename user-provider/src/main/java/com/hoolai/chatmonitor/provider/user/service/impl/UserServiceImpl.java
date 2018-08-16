package com.hoolai.chatmonitor.provider.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.user.service.UserService;

@Service(timeout = 10000, retries = 0)
public class UserServiceImpl implements UserService {

    @Override
    public void validateUser(Long uid) {
        if (uid == null || uid < 1000) {
            throw new HException(HExceptionEnum.UID_NOT_FIND);
        }
    }
}
