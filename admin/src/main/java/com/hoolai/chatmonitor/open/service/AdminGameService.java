package com.hoolai.chatmonitor.open.service;

import java.util.List;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;


public interface AdminGameService {
		
	//新增产品
	ReturnValue<AdminGame> add(Long gameId,String gameName,Integer groupId) throws HException;

	//修改产品
	ReturnValue<AdminGame> update( Long gameId,String gameName,Integer groupId) throws HException;
	
	//获取产品
	ReturnValue<List<AdminGame>> get(Long gameId,String gameName,Integer groupId) throws HException;
}
