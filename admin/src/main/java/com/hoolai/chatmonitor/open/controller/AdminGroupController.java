package com.hoolai.chatmonitor.open.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.service.AdminGroupService;


@RestController
@RequestMapping("group")
public class AdminGroupController {

	@Resource
	private AdminGroupService adminGroupService;	
	
	@GetMapping("add")//新增组
	public ReturnValue<AdminGroup> register(HttpServletRequest request,String name) {

		ReturnValue<AdminGroup> returnVal=adminGroupService.add(name);	    		    
        return returnVal;
	 }
	
	@GetMapping("update")//更改组信息
	public ReturnValue<AdminGroup> updateUserInfo(HttpServletRequest request,Integer groupId,String name) {

		ReturnValue<AdminGroup> returnVal=adminGroupService.update(groupId, name);	    		    
        return returnVal;
	 }
	

}
