package com.hoolai.chatmonitor.provider.process.dao.mybatis.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.hoolai.chatmonitor.provider.process.dao.MsgSuspiciousDao;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper.MsgSuspiciousMapper;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspiciousExample;

import java.lang.Long;
import java.util.List;
import java.util.Map;

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
	@Override
	public List<MsgSuspicious> list(MsgSuspicious property,List<Long> gameIds) {
		
		MsgSuspiciousExample example = new MsgSuspiciousExample();
		MsgSuspiciousExample.Criteria ca = example.createCriteria();
		
		
		if (!Strings.isNullOrEmpty(property.getMsg())) {
			ca.andMsgLike("%"+property.getMsg()+"%");
		}
		if (property.getUid() != null && property.getUid() != 0L) {
			ca.andUidEqualTo(property.getUid());
		}
		
		if (gameIds!= null && gameIds.size()>0) {
			ca.andGameIdIn(gameIds);
		}else if (property.getGameId() != null && property.getGameId() != 0L) {
			ca.andGameIdEqualTo(property.getGameId());
		}else{
			//null
		}
		
		example.setOrderByClause("status asc,create_time asc");
		return msgSuspiciousMapper.selectByExample(example);
	}

	@Override
	public PageInfo<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious> selectSuspiciousMapList(String account,
			String gameName, String msg,Byte status, Integer gameId, Integer groupId,Integer pageNum,Integer pageSize) {
		
		pageNum=pageNum==null?0:pageNum;		
		pageSize=pageSize==null?3:pageSize;
		
		PageHelper.startPage(pageNum, pageSize);
		
		List<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious> list= msgSuspiciousMapper.selectSuspiciousMapList(account, gameName, msg,status, gameId, groupId);
		PageInfo<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious> pageInfo = new PageInfo<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious>(list);  
	
		return pageInfo;
	}
	
	//**********自定义结束*****////
	
}
