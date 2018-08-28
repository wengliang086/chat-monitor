package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.google.common.base.Strings;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminGroupMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroupExample;
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

	@Override
	public List<AdminGroup> list(String name) {
		AdminGroupExample example = new AdminGroupExample();
		AdminGroupExample.Criteria criteria = example.createCriteria();
		if (!Strings.isNullOrEmpty(name)) {
			criteria.andGroupNameLike(name);
		}
		List<AdminGroup> adminGroups = adminGroupMapper.selectByExample(example);
		return adminGroups == null ? new ArrayList<>() : adminGroups;
	}

	//**********自定义结束*****////
	
}
