package com.hoolai.chatmonitor.open.service;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;


public interface AdminUserService {
	
	/**
	 * 根据uid获取用户
	 * @param udid
	 * @return
	 * @throws HException
	 */
	ReturnValue<AdminUser> loginByUdid(long udid) throws HException;
	
	/**
	 * 根据账号获取用户
	 * @param account
	 * @param password
	 * @throws HException
	 * @return
	 */
	ReturnValue<AdminUser> loginByAccount(String account, String password) throws HException;
	
	/**
	 * @param account         账户名
	 * @param password        密码
	 * @param email           邮箱
	 * @param phone           电话
	 * @return
	 * @throws HException
	 */
	
	//注册用户信息
	ReturnValue<AdminUser> register(String account, String password, String email, String phone, Integer groupId) throws HException;

	//更新用户信息
	ReturnValue<AdminUser> updateLoginInfo(Long uid, String account, String password, String email, String phone,Integer groupId) throws HException;
}
