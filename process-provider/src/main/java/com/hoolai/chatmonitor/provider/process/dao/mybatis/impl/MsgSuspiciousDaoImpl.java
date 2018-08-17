package com.hoolai.chatmonitor.provider.process.dao.mybatis.impl;

import com.hoolai.chatmonitor.provider.process.dao.MsgSuspiciousDao;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper.MsgSuspiciousMapper;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class MsgSuspiciousDaoImpl implements MsgSuspiciousDao {
	
	@Resource
	private MsgSuspiciousMapper msgSuspiciousMapper;

	@Override
	public void save(MsgSuspicious msgSuspicious){
		msgSuspiciousMapper.insert(msgSuspicious);
	}

	@Override
	public MsgSuspicious get(Long id) {
		return msgSuspiciousMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(MsgSuspicious msgSuspicious) {
		msgSuspiciousMapper.updateByPrimaryKey(msgSuspicious);
	}

	@Override
	public void delete(Long id) {
		msgSuspiciousMapper.deleteByPrimaryKey(id);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
