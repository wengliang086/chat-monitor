package com.hoolai.chatmonitor.open.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminUserService;


@RestController
@RequestMapping("user")
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
	
	
	/**根据账号密码登录
	 * 
	 * @param account 账号 (必填)
	 * @param password 密码(必填)
	 * 
	 * */
	@CrossOrigin
	@RequestMapping("loginByAccount")
	public ReturnValue<AdminUser> login(HttpServletRequest request,String account, String password) {

		ReturnValue<AdminUser> returnVal=adminUserService.loginByAccount(account, password);	    		    
		LoginContext.setAdminUser(request, returnVal.getValue());	    
        return returnVal;
	 }
	
	
	/**新增新用户
	 * 
	 * @param account 账号 (必填)
	 * @param password 密码(可选) 默认:123456
	 * @param email 邮箱 (可选)
	 * @param phone 手机号(可选)
	 * @param groupId 用户组(必填)
	 * 
	 * */
	@GetMapping("register")
	public ReturnValue<AdminUser> register(HttpServletRequest request,String account, String password,String email, String phone, Integer groupId) {

		ReturnValue<AdminUser> returnVal=adminUserService.register(account, password, email, phone, groupId);	    		    
        return returnVal;
	 }
	
	
	/**更改用户
	 * 
	 * @param uid admin_user中的uid(必填)
	 * @param password 密码(可选) 
	 * @param email 邮箱 (可选)
	 * @param phone 手机号(可选)
	 * @param groupId 用户组(可选)
	 * 
	 * */
	@GetMapping("updateUserInfo")
	public ReturnValue<AdminUser> updateUserInfo(HttpServletRequest request,Long uid, String password,String email, String phone, Integer groupId) {

		ReturnValue<AdminUser> returnVal=adminUserService.updateLoginInfo(uid, password, email, phone, groupId);	    		    
		AdminUser user=LoginContext.getLoginUser(request);
		if(user!=null && null!=returnVal.getValue() && user.getUid()==returnVal.getValue().getUid()) {
			LoginContext.setAdminUser(request, returnVal.getValue());//更新用户在缓存中的信息
		}
		    
        return returnVal;
	 }
	
	

}
