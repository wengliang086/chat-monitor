package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminGameMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import java.lang.Long;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;




@Repository
public class AdminGameDaoImpl implements AdminGameDao {
	
	@Resource
	private AdminGameMapper adminGameMapper;

	@Override
	public void save(AdminGame adminGame){
		adminGameMapper.insert(adminGame);
	}

	@Override
	public AdminGame get(Long gameId) {
		return adminGameMapper.selectByPrimaryKey(gameId);
	}

	@Override
	public void update(AdminGame adminGame) {
		adminGameMapper.updateByPrimaryKey(adminGame);
	}

	@Override
	public void delete(Long gameId) {
		adminGameMapper.deleteByPrimaryKey(gameId);
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
