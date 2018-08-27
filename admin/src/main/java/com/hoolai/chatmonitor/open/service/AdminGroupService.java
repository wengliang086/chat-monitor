package com.hoolai.chatmonitor.open.service;

import java.util.List;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;


public interface AdminGroupService {
		
	//新增用户组
	ReturnValue<AdminGroup> add(String groupName) throws HException;

	//修改用户组信息
	ReturnValue<AdminGroup> update(Integer groupId, String groupName) throws HException;
	
	//用户组列表
	ReturnValue<List<AdminGroup>> list(Integer groupId,String groupName,List<Integer>groupIds) throws HException;
}
