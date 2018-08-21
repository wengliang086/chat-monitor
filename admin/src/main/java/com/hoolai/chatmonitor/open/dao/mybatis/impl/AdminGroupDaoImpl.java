package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminGroupMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class AdminGroupDaoImpl implements AdminGroupDao {
	
	@Resource
	private AdminGroupMapper adminGroupMapper;

	@Override
	public void save(AdminGroup adminGroup){
		adminGroupMapper.insert(adminGroup);
	}

	@Override
	public AdminGroup get(Integer groupId) {
		return adminGroupMapper.selectByPrimaryKey(groupId);
	}

	@Override
	public void update(AdminGroup adminGroup) {
		adminGroupMapper.updateByPrimaryKey(adminGroup);
	}

	@Override
	public void delete(Integer groupId) {
		adminGroupMapper.deleteByPrimaryKey(groupId);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
