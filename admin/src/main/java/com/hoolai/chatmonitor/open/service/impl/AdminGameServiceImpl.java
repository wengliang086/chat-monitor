package com.hoolai.chatmonitor.open.service.impl;


import java.util.List;
import javax.annotation.Resource;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGameService;

@Service
@Transactional
public class AdminGameServiceImpl implements AdminGameService{

	@Resource
	private AdminGameDao adminGameDao;
	
	@Resource
	private AdminGroupDao adminGropuDao;

	@Override
	public ReturnValue<AdminGame> add(String gameName,Integer groupId) throws HException {
		if(Strings.isNullOrEmpty(gameName)){
			throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_IS_INVALID).build();
		}
		existGroup(groupId);//检测group是否存
		
		AdminGame game=new AdminGame();
		game.setGameName(gameName);
		game.setGroupId(groupId);
		adminGameDao.save(game);
		return createLoginResult(game);
	}

	@Override
	public ReturnValue<AdminGame> update(Long gameId,String gameName,Integer groupId) throws HException {
		
		
		if(gameId==null || gameId==0){
			throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_ID_IS_NULL).build();
		}
		
		AdminGame game=adminGameDao.get(gameId);
		
		if(game==null){
			throw HExceptionBuilder.newBuilder(HExceptionEnum.GAME_NOT_IEXIST).build();
		}

		game.setGameName(Strings.isNullOrEmpty(gameName)?game.getGameName():gameName);
		
		if(!(null==groupId || groupId==0)){
			existGroup(groupId);//检测group是否存
			game.setGroupId(groupId);
		}		
		
		adminGameDao.update(game);
		
		return createLoginResult(game);
	}
	
	private void existGroup(Integer groupId){
		
		if(groupId==null || groupId==0){
			throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_ID_IS_NULL).build();
		}
		
		AdminGroup group=adminGropuDao.get(groupId);
		if(group==null){
			throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NOT_IEXIST).build();
		}
	}
	
	
	//生成returnvalue
	private ReturnValue<AdminGame> createLoginResult(AdminGame game) {
		ReturnValue<AdminGame> returnVal=new ReturnValue<AdminGame>();
		returnVal.setValue(game);
		return returnVal;
	}

	@Override
	public ReturnValue<List<AdminGame>> get(Long gameId,String gameName,Integer groupId)
			throws HException {
		
		AdminGame property=new AdminGame();
		property.setGameId(gameId);
		property.setGameName(gameName);
		property.setGroupId(groupId);
		
		List<AdminGame> list=adminGameDao.get(property);
		ReturnValue<List<AdminGame>> returnVal=new ReturnValue<List<AdminGame>>();
		returnVal.setValue(list);
		return returnVal;
	}
	

}
