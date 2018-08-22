package com.hoolai.chatmonitor.open.service.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.service.AdminGameService;

@Service
@Transactional
public class AdminGameServiceImpl implements AdminGameService{

	
	@Resource
	private AdminGameDao adminGameDao;

	@Override
	public ReturnValue<AdminGame> add(String gameName,Integer groupId) throws HException {
		AdminGame game=new AdminGame();
		game.setGameName(gameName);
		game.setGroupId(groupId);
		adminGameDao.save(game);
		return createLoginResult(game);
	}

	@Override
	public ReturnValue<AdminGame> update(Long gameId,String gameName,Integer groupId) throws HException {
		AdminGame game=new AdminGame();
		game.setGameId(gameId);
		game.setGameName(gameName);
		game.setGroupId(groupId);
		adminGameDao.update(game);
		return createLoginResult(game);
	}
	
	
	//生成returnvalue
	private ReturnValue<AdminGame> createLoginResult(AdminGame game) {
		ReturnValue<AdminGame> returnVal=new ReturnValue<AdminGame>();
		returnVal.setValue(game);
		return returnVal;
	}
	

}
