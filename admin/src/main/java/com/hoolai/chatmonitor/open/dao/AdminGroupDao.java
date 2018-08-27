package com.hoolai.chatmonitor.open.dao;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;

import java.lang.Integer;
import java.util.List;


public interface AdminGroupDao extends Dao<AdminGroup,Integer>{

	
	////*******自定义开始********//
	List<AdminGroup> list(Integer groupId, String groupName,List<Integer> groupIds);
	//**********自定义结束*****////

}
