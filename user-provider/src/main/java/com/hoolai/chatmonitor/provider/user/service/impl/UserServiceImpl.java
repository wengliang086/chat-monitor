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
    public Long validateUser(long gameId, String gameUid) {
        /*
         * 验证：
         * 1、uid是否存在 用户表中
         * 2、是否被封号
         */
        UserLoginInfo userLoginInfo = userLoginInfoDao.getByGameInfo(gameId, gameUid);
        if (userLoginInfo == null) {
            userLoginInfo = new UserLoginInfo();
            userLoginInfo.setCreateTime(new Date());
            userLoginInfo.setGameId(gameId);
            userLoginInfo.setGameUid(gameUid);
            userLoginInfoDao.save(userLoginInfo);
        }
        UserFreeze userFreeze = userFreezeDao.get(userLoginInfo.getUid());
        if (userFreeze != null) {
            throw new HException(HExceptionEnum.UID_ALREADY_FREEZED);
        }
        return userLoginInfo.getUid();
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
