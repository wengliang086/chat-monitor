package com.hoolai.chatmonitor.open.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Strings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGroupService;

@Service
@Transactional
public class AdminGroupServiceImpl implements AdminGroupService{

	
	@Resource
	private AdminGroupDao adminGropuDao;

	@Override
	public ReturnValue<AdminGroup> add(String groupName) throws HException {
		AdminGroup group=new AdminGroup();
		group.setGroupName(groupName);
		adminGropuDao.save(group);
		return createLoginResult(group);
	}

	@Override
	public ReturnValue<AdminGroup> update(Integer groupId,
			String groupName) throws HException {
		
		if(groupId==null || groupId==0){
			throw new HException( new DefaultReturnCode(null,-1,"groupId_is_null") );
		}
		
		AdminGroup group=adminGropuDao.get(groupId);
		
		if(group==null){
			throw new HException( new DefaultReturnCode(null,-1,"group_not_exist") );
		}

		if(Strings.isNullOrEmpty(groupName)){
			throw new HException( new DefaultReturnCode(null,-1,"group_name_is_null") );
		}
		
		group.setGroupName(groupName);	
		adminGropuDao.update(group);
		return createLoginResult(group);
	}
	
	
	//生成returnvalue
	private ReturnValue<AdminGroup> createLoginResult(AdminGroup group) {
		ReturnValue<AdminGroup> returnVal=new ReturnValue<AdminGroup>();
		returnVal.setValue(group);
		return returnVal;
	}

	@Override
	public ReturnValue<List<AdminGroup>> list(Integer groupId, String groupName,
			List<Integer> groupIds) throws HException {
		
		List<AdminGroup> list=adminGropuDao.list(groupId, groupName, groupIds);
		ReturnValue<List<AdminGroup>> returnVal=new ReturnValue<List<AdminGroup>>();
		returnVal.setValue(list);
		return returnVal;
	}
	

}
