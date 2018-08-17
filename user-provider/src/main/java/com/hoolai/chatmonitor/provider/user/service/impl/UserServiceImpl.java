package com.hoolai.chatmonitor.provider.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.user.dao.UserFreezeDao;
import com.hoolai.chatmonitor.provider.user.dao.UserLoginInfoDao;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserFreeze;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfo;
import com.hoolai.chatmonitor.provider.user.service.UserService;

import javax.annotation.Resource;
import java.util.Date;

@Service(timeout = 10000, retries = 0)
public class UserServiceImpl implements UserService {

    @Resource
    private UserLoginInfoDao userLoginInfoDao;

    @Resource
    private UserFreezeDao userFreezeDao;

    @Override
    public void validateUser(Long uid) {
        /*
         * 验证：
         * 1、uid是否存在 用户表中
         * 2、是否被封号
         */
        UserLoginInfo userLoginInfo = userLoginInfoDao.get(uid);
        if (userLoginInfo == null) {
//            throw new HException(HExceptionEnum.UID_NOT_FIND);
            throw HExceptionBuilder.newBuilder(HExceptionEnum.UID_NOT_FIND).build();
        }
        UserFreeze userFreeze = userFreezeDao.get(uid);
        if (userFreeze != null) {
            throw new HException(HExceptionEnum.UID_ALREADY_FREEZED);
        }
    }

    @Override
    public void freeze(Long uid) throws HException {
        UserFreeze userFreeze = new UserFreeze();
        userFreeze.setFreezed(true);
        userFreeze.setUid(uid);
        userFreeze.setUpdateTime(new Date());
        userFreezeDao.save(userFreeze);
    }
}
