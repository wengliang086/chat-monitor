package com.hoolai.chatmonitor.open.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGroup;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
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
	
	/**当前用户所属组下的game列表
	 * 
	 * @param groupId groupId (可选)
	 * @param name groupName (可选)
	 * @param groupIds groupIds (可选)
	 * 
	 * */
	@GetMapping("list")
	public ReturnValue<List<AdminGroup>> list(HttpServletRequest request,Integer groupId,String groupName,List<Integer>groupIds) {
		
		if( (groupId==null || groupId==0) && (groupIds==null || groupIds.size()==0) ){
			
			AdminUser user=LoginContext.getLoginUser(request);
			
			if(!user.getAccount().equals("admin")){
				groupId=user.getGroupId();
			}			
		}
		
		ReturnValue<List<AdminGroup>> gameVal=adminGroupService.list(groupId, groupName, groupIds);     
        return gameVal;
	 }

}
