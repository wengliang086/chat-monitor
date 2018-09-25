package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.hoolai.chatmonitor.open.dao.AdminPermissionDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminPermissionMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermission;
import java.lang.Long;
import java.util.List;
import javax.annotation.Resource;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermissionExample;
import org.springframework.stereotype.Repository;




@Repository
public class AdminPermissionDaoImpl implements AdminPermissionDao {
	
	@Resource
	private AdminPermissionMapper adminPermissionMapper;

	@Override
	public void save(AdminPermission adminPermission){
		adminPermissionMapper.insert(adminPermission);
	}

	@Override
	public AdminPermission get(Long id) {
		return adminPermissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(AdminPermission adminPermission) {
		adminPermissionMapper.updateByPrimaryKey(adminPermission);
	}

	@Override
	public void delete(Long id) {
		adminPermissionMapper.deleteByPrimaryKey(id);
	}
	
	 
	////*******自定义开始********//

	@Override
	public List<AdminPermission> getPermissions(boolean isAdmin) {
		AdminPermissionExample example = new AdminPermissionExample();
		AdminPermissionExample.Criteria criteria = example.createCriteria();
		if (!isAdmin) {
			criteria.andAdminIsNull();
		}
		return adminPermissionMapper.selectByExample(example);
	}

	//**********自定义结束*****////
	
}
