package com.hoolai.chatmonitor.open.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminUserService;


@RestController
@RequestMapping("admin/user")
public class AdminUserController {

	@Resource
	private AdminUserService adminUserService;
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		AdminUser loginUser = LoginContext.getLoginUser(request);
		if(loginUser != null) {
			String url = "main";
			ModelAndView model = new ModelAndView(url);
			model.addObject("loginUser",loginUser);
			return model;
		}
		return new ModelAndView("user/login");
	}
	
	
	@RequestMapping("loginByAccount")//根据账号密码登录
	public ReturnValue<AdminUser> login(HttpServletRequest request,String account, String password) {

		ReturnValue<AdminUser> returnVal=adminUserService.loginByAccount(account, password);	    		    
		LoginContext.setAdminUser(request, returnVal.getValue());	    
        return returnVal;
	 }
	
	
	@GetMapping("register")//新增新用户
	public ReturnValue<AdminUser> register(HttpServletRequest request,String account, String password,String email, String phone, Integer groupId) {

		ReturnValue<AdminUser> returnVal=adminUserService.register(account, password, email, phone, groupId);	    		    
        return returnVal;
	 }
	
	@GetMapping("updateUserInfo")//更改用户
	public ReturnValue<AdminUser> updateUserInfo(HttpServletRequest request,Long uid,String account, String password,String email, String phone, Integer groupId) {

		ReturnValue<AdminUser> returnVal=adminUserService.updateLoginInfo(uid, account, password, email, phone, groupId);	    		    
		AdminUser user=LoginContext.getLoginUser(request);
		if(user!=null && user.getUid()==uid){
			LoginContext.setAdminUser(request, returnVal.getValue());//更新用户在缓存中的信息
		}
		    
        return returnVal;
	 }
	
	

}
