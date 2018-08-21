package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hoolai.chatmonitor.open.dao.AdminUserDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminUserMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import java.lang.Long;

@Repository
public class AdminUserDaoImpl implements AdminUserDao {
	
	@Resource
	private AdminUserMapper adminUserMapper;

	@Override
	public void save(AdminUser adminUser){
		adminUserMapper.insert(adminUser);
	}

	@Override
	public AdminUser get(Long uid) {
		return adminUserMapper.selectByPrimaryKey(uid);
	}

	@Override
	public void update(AdminUser adminUser) {
		adminUserMapper.updateByPrimaryKey(adminUser);
	}

	@Override
	public void delete(Long uid) {
		adminUserMapper.deleteByPrimaryKey(uid);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
