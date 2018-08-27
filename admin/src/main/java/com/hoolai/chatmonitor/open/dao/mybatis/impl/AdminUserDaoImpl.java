package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hoolai.chatmonitor.open.dao.AdminUserDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminUserMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUserExample;

import java.lang.Long;
import java.util.List;
import java.util.Map;

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

	@Override
	public AdminUser getByAccountAndPwd(String account, String password) {
		AdminUserExample loginInfoExample = new AdminUserExample();
		loginInfoExample.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(password);
		return getByExample(loginInfoExample);
	}
	
	private AdminUser getByExample(AdminUserExample loginInfoExample) {
		List<AdminUser> loginInfoList = adminUserMapper.selectByExample(loginInfoExample);
		if (null == loginInfoList || loginInfoList.isEmpty()) {
			return null;
		}
		return loginInfoList.get(0);
	}

	@Override
	public AdminUser getByPassport(String account, String email,
			String phone) {	
		
		AdminUserExample loginInfoExample = new AdminUserExample();
		loginInfoExample.createCriteria().andAccountEqualTo(account);
		return getByExample(loginInfoExample);
	}

	@Override
	public List<Map<String, Object>> selectUserMapList(String account, String email,
			String phone, Integer groupId) {
		
		return adminUserMapper.selectUserMapList(account, email, phone, groupId);
	}
	
	 
	////*******自定义开始********//
	
	
	
	//**********自定义结束*****////
	
}
