package com.hoolai.chatmonitor.provider.process.dao;

import java.util.List;

import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;


public interface MsgSuspiciousDao extends Dao<MsgSuspicious,Long>{

	
	////*******自定义开始********//
	List<MsgSuspicious> list(MsgSuspicious property);
	//**********自定义结束*****////

}
