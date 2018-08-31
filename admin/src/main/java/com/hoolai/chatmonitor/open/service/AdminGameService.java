package com.hoolai.chatmonitor.open.service;

import java.util.List;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;


public interface AdminGameService {
		
	//新增产品
	AdminGame add(String gameName,Integer groupId) throws HException;

	//修改产品
	AdminGame update( Long gameId,String gameName,Integer groupId) throws HException;
	
	//获取产品
	List<AdminGame> get(Long gameId,String gameName,Integer groupId) throws HException;
}
