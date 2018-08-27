package com.hoolai.chatmonitor.provider.process.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;


public interface MsgSuspiciousDao extends Dao<MsgSuspicious,Long>{

	
	////*******自定义开始********//
	List<MsgSuspicious> list(MsgSuspicious property,List<Long> gameId);
    List<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious> selectSuspiciousMapList(String account,String gameName,String msg,Byte status,Integer gameId,Integer groupId);

	//**********自定义结束*****////

}
