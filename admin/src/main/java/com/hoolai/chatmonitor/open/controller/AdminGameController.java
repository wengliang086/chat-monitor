package com.hoolai.chatmonitor.open.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.service.AdminGameService;


@RestController
@RequestMapping("admin/game")
public class AdminGameController {

	@Resource
	private AdminGameService adminGameService;	
	
	@GetMapping("add")//新增组
	public ReturnValue<AdminGame> register(HttpServletRequest request,String name,Integer groupId) {

		ReturnValue<AdminGame> returnVal=adminGameService.add(name,groupId);	    		    
        return returnVal;
	 }
	
	@GetMapping("update")//更改组信息
	public ReturnValue<AdminGame> updateUserInfo(HttpServletRequest request,Long gameId,String name,Integer groupId) {

		ReturnValue<AdminGame> returnVal=adminGameService.update(gameId, name,groupId);	    		    
        return returnVal;
	 }
	

}
