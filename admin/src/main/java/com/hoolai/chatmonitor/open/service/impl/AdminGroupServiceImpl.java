package com.hoolai.chatmonitor.open.service.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
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
		AdminGroup group=new AdminGroup();
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
	

}
