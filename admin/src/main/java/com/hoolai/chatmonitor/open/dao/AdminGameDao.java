package com.hoolai.chatmonitor.open.dao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;

import java.lang.Long;
import java.util.List;


public interface AdminGameDao extends Dao<AdminGame,Long>{

	
	////*******自定义开始********//
	List<AdminGame> get(AdminGame property);
	//**********自定义结束*****////

}
