package com.hoolai.chatmonitor.provider.user.dao.mybatis.impl;

import com.hoolai.chatmonitor.provider.user.dao.UserLoginInfoDao;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.mapper.UserLoginInfoMapper;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfo;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class UserLoginInfoDaoImpl implements UserLoginInfoDao {
	
	@Resource
	private UserLoginInfoMapper userLoginInfoMapper;

	@Override
	public void save(UserLoginInfo userLoginInfo){
		userLoginInfoMapper.insert(userLoginInfo);
	}

	@Override
	public UserLoginInfo get(Long uid) {
		return userLoginInfoMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void update(UserLoginInfo userLoginInfo) {
		userLoginInfoMapper.updateByPrimaryKey(userLoginInfo);
	}

	@Override
	public void delete(Long uid) {
		userLoginInfoMapper.deleteByPrimaryKey(uid);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
