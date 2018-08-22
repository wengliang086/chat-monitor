package com.hoolai.chatmonitor.open.dao;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;

import java.lang.Long;
import java.util.List;

public interface AdminUserDao extends Dao<AdminUser,Long>{

	
	////*******自定义开始********//
	AdminUser getByAccountAndPwd(String account, String password);
	AdminUser getByPassport(String account,String email,String phone);
	//**********自定义结束*****////

}
