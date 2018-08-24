package com.hoolai.chatmonitor.open.dao.mybatis.impl;

import com.google.common.base.Strings;
import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.mybatis.mapper.AdminGameMapper;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGameExample;

import java.lang.Long;
import java.util.List;

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
	
	@Override
	public List<AdminGame> get(AdminGame property) {
		AdminGameExample example = new AdminGameExample();
		AdminGameExample.Criteria ca = example.createCriteria();
		
		if (property.getGameId() != null && property.getGameId() != 0L) {
			ca.andGameIdEqualTo(property.getGameId());
		}
		if (!Strings.isNullOrEmpty(property.getGameName())) {
			ca.andGameNameLike("%" +property.getGameName()+"%");
		}
		if (property.getGroupId() != null && property.getGroupId() != 0L) {
			ca.andGroupIdEqualTo(property.getGroupId());
		}
		
		example.setOrderByClause("game_id");
		return adminGameMapper.selectByExample(example);
	
	}
	
	 
	////*******自定义开始********//
	//**********自定义结束*****////
	
}
