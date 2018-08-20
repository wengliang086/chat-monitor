package com.hoolai.chatmonitor.provider.process.dao.mybatis.impl;

import com.hoolai.chatmonitor.provider.process.dao.MsgIllegalWordDao;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper.MsgIllegalWordMapper;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgIllegalWord;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class MsgIllegalWordDaoImpl implements MsgIllegalWordDao {
	
	@Resource
	private MsgIllegalWordMapper msgIllegalWordMapper;

	@Override
	public void save(MsgIllegalWord msgIllegalWord){
		msgIllegalWordMapper.insert(msgIllegalWord);
	}

	@Override
	public MsgIllegalWord get(Long id) {
		return msgIllegalWordMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(MsgIllegalWord msgIllegalWord) {
		msgIllegalWordMapper.updateByPrimaryKey(msgIllegalWord);
	}

	@Override
	public void delete(Long id) {
		msgIllegalWordMapper.deleteByPrimaryKey(id);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
