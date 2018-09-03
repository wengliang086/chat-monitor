package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.hoolai.chatmonitor.open.dao.AdminLogDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminLogMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminLog;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class AdminLogDaoImpl implements AdminLogDao {
	
	@Resource
	private AdminLogMapper adminLogMapper;

	@Override
	public void save(AdminLog adminLog){
		adminLogMapper.insert(adminLog);
	}

	@Override
	public AdminLog get(Long logId) {
		return adminLogMapper.selectByPrimaryKey(logId);
	}

	@Override
	public void update(AdminLog adminLog) {
		adminLogMapper.updateByPrimaryKeyWithBLOBs(adminLog);
	}

	@Override
	public void delete(Long logId) {
		adminLogMapper.deleteByPrimaryKey(logId);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
