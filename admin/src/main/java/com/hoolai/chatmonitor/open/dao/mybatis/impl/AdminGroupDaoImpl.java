package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.google.common.base.Strings;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminGroupMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGameExample;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroupExample;

import java.lang.Integer;
import java.util.List;

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

	@Override
	public List<AdminGroup> list(Integer groupId, String groupName,
			List<Integer> groupIds) {
		
		AdminGroupExample example = new AdminGroupExample();
		AdminGroupExample.Criteria ca = example.createCriteria();
		
		
		if (!Strings.isNullOrEmpty(groupName)) {
			ca.andGroupNameLike("%" +groupName+"%");
		}
		
		if (groupId != null && groupId != 0) {
			ca.andGroupIdEqualTo(groupId);
		}else if (groupIds != null && groupIds.size()>0) {
			ca.andGroupIdIn(groupIds);
		}else{
			//none
		}
		
		example.setOrderByClause("group_id desc");
		return adminGroupMapper.selectByExample(example);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
