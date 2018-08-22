package com.hoolai.chatmonitor.open.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.service.AdminGameService;

public class SuspiciousMsgController {

	@Resource
	private AdminGameService adminGameService;	
	
	@GetMapping("list")//新增组
	public ReturnValue<AdminGame> register(HttpServletRequest request,String uid,String msg,String groupId,String illegalWords,Integer opUId) {

		//admin可根据上面所有参数查询可疑信息，但具体的用户只能根据（msg or illegalWords）and 自己的groupid and 自己的groupid
		//ReturnValue<AdminGame> returnVal=adminGameService.add(name,groupId);	    		    
        //return returnVal;
		return null;
	 }
}
