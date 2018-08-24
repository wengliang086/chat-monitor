package com.hoolai.chatmonitor.open.service.impl;


import java.util.regex.Pattern;

import javax.annotation.Resource;







import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;







import com.google.common.base.Strings;
import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGroupDao;
import com.hoolai.chatmonitor.open.dao.AdminUserDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminUserService;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService{

	
	@Resource
	private AdminUserDao adminUserDao;
	
	@Resource
	private AdminGroupDao adminGropuDao;
	
	//根据uid获取用户信息
	@Override
	public ReturnValue<AdminUser> loginByUdid(long uid) throws HException {
		AdminUser loginInfo = adminUserDao.get(uid);
		if (loginInfo == null) {
			return createLoginResult ("UID_NOT_FIND");
		}		
		 return createLoginResult(loginInfo);
	}

	//根据账号、密码获取用户信息
	@Override
	public ReturnValue<AdminUser> loginByAccount(String account, String password)
			throws HException {
		AdminUser loginInfo = adminUserDao.getByAccountAndPwd(account, encryPassword(password));
		if (loginInfo == null) {
			return createLoginResult ("ACCOUNT_INFO_NOT_FOUND");
		}
		return createLoginResult(loginInfo);
	}	
	
	//生成returnvalue
	private ReturnValue<AdminUser> createLoginResult(AdminUser user) {
		ReturnValue<AdminUser> returnVal=new ReturnValue<AdminUser>();
		returnVal.setValue(user);
		return returnVal;
	}
	private ReturnValue<AdminUser> createLoginResult(String msg) {
		ReturnValue<AdminUser> returnVal=new ReturnValue<AdminUser>(new DefaultReturnCode(null,-1,msg));		
		return returnVal;
	}
	
	//密码加密
	private String encryPassword(String password) {
		if (password.length() == 32) {
			password = DigestUtils.md5DigestAsHex(password.getBytes());
		} else {
			password = DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(password.getBytes()).getBytes());
		}
		return password;
	}

	//注册新用户信息
	@Override
	public ReturnValue<AdminUser> register(String account, String password,
			String email, String phone, Integer groupId) throws HException {
		if (Strings.isNullOrEmpty(account)) {
			return createLoginResult ("ACCOUNT_IS_NULL");
		}
		
	    checkAccount(account);
	    // 帐号可以输入邮箱格式，如果是邮箱格式帐号的话，两列都存
	    if (isEmail(account) && Strings.isNullOrEmpty(email)) {
	    	email = account;
	    }	    	   
		
	    AdminUser loginInfo = adminUserDao.getByPassport(account, null, null);//根据账号查找是否已经存在过了

		if (isAccountExist(loginInfo)) {
			return createLoginResult ("ACCOUNT_ALREDAY_EXIST");
		}
		
		if(Strings.isNullOrEmpty(password)){
			password="123456";
		}else{
			checkPassword(password);//检查密码的合法性
		}
		
		password = encryPassword(password);
		
		existGroup(groupId);
		
		AdminUser registerInfo = new AdminUser();
		registerInfo.setAccount(account);
		registerInfo.setEmail(email);
		registerInfo.setPassword(password);
		registerInfo.setPhone(phone);
		registerInfo.setGroupId(groupId);
		adminUserDao.save(registerInfo);//新用户入库
	    
		return createLoginResult(registerInfo);
	}
	
	//判断账号是否合法
	private void checkAccount(String account) {
		if (Strings.isNullOrEmpty(account) || (!Pattern.matches("^[a-zA-Z][a-zA-z_0-9]*[a-zA-Z0-9]$", account))) {
			throw  HExceptionBuilder.newBuilder(new DefaultReturnCode(null,-1,"ACCOUNT_IS_INVALID")).build();
		}		
	}
	//判断email是否合法
	private boolean isEmail(String email) {
		return email != null && Pattern.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", email);
	}
	//判断密码是否合法
	private void checkPassword(String password) {
		if (Strings.isNullOrEmpty(password)) {
			throw HExceptionBuilder.newBuilder(HExceptionEnum.PASSWORD_CAN_NOT_BE_EMPTY).build();
		}
		if (password.length() == 32) {
			return;
		}
		if (!Pattern.matches("[0-9a-zA-z`~!@#\\$%\\^&\\*\\(\\)\\-_=+\\[\\]\\{\\};:'\",<>./?\\\\|]{6,20}", password)) {
			throw HExceptionBuilder.newBuilder(HExceptionEnum.PASSWORD_IS_INVALID).build();
		}
	}
	//判断账号是否存在
	private boolean isAccountExist(AdminUser loginInfo) {
		if (loginInfo != null) {		
			return true;						
		}
		return false;
	}
	
	private void existGroup(Integer groupId){
		
		if(groupId==null || groupId==0){
			throw new HException( new DefaultReturnCode(null,-1,"group_id_is_null") );
		}
		
		AdminGroup group=adminGropuDao.get(groupId);
		if(group==null){
			throw new HException( new DefaultReturnCode(null,-1,"group_not_exist") );
		}
	}

	//修改用户信息
	@Override
	public ReturnValue<AdminUser> updateLoginInfo(Long uid,
			String password, String email, String phone, Integer groupId)
			throws HException {
		
		 AdminUser userInfo = adminUserDao.get(uid);
		 if(userInfo==null){
			 return createLoginResult ("ACCOUNT_INFO_NOT_FOUND");
		 }

	    // 帐号可以输入邮箱格式，如果是邮箱格式帐号的话，两列都存
	    if (Strings.isNullOrEmpty(email) && isEmail(userInfo.getAccount())) {
	    	email = userInfo.getAccount();
	    }
	    
	    if(!Strings.isNullOrEmpty(password)){
	    	checkPassword(password);//检查密码的合法性
	    	password = encryPassword(password);
			userInfo.setPassword(password);
	    }
	   
	    if(!(null==groupId || groupId==0)){
			existGroup(groupId);//检测group是否存
			userInfo.setGroupId(groupId);
		}		
						
		userInfo.setEmail(Strings.isNullOrEmpty(email)?userInfo.getEmail():email);
		userInfo.setPhone(Strings.isNullOrEmpty(phone)?userInfo.getPhone():phone);

		adminUserDao.update(userInfo);//修改用户
	    
		return createLoginResult(userInfo);
	}

}
