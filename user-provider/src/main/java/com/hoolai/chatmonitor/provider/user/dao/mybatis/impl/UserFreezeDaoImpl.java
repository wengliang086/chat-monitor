package com.hoolai.chatmonitor.provider.user.dao.mybatis.impl;

import com.hoolai.chatmonitor.provider.user.dao.UserFreezeDao;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.mapper.UserFreezeMapper;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserFreeze;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class UserFreezeDaoImpl implements UserFreezeDao {
	
	@Resource
	private UserFreezeMapper userFreezeMapper;

	@Override
	public void save(UserFreeze userFreeze){
		userFreezeMapper.insert(userFreeze);
	}

	@Override
	public UserFreeze get(Long uid) {
		return userFreezeMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void update(UserFreeze userFreeze) {
		userFreezeMapper.updateByPrimaryKey(userFreeze);
	}

	@Override
	public void delete(Long uid) {
		userFreezeMapper.deleteByPrimaryKey(uid);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
