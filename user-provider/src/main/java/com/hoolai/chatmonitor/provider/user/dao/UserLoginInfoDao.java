package com.hoolai.chatmonitor.provider.user.dao;

import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfo;
import java.lang.Long;


public interface UserLoginInfoDao extends Dao<UserLoginInfo,Long>{

	
	////*******自定义开始********//
    UserLoginInfo getByGameInfo(long gameId, String gameUid);
	//**********自定义结束*****////

}
