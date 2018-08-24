package com.hoolai.chatmonitor.open.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.service.AdminGameService;
import com.hoolai.chatmonitor.open.service.AdminGroupService;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import com.hoolai.chatmonitor.provider.user.service.UserService;

@RestController
@RequestMapping("suspicious")
public class SuspiciousMsgController {

	@Resource
	private AdminGameService adminGameService;
	
	@Resource
	private AdminGroupService adminGroupService;	
	
	@Reference
    private CheckService checkService;
	
    @Reference
    private UserService userService;
	
	
    
    /**查询可疑信息列表
	 * 
	 * @param uid 可根据u_login_info中的uid查询 (可选)
	 * @param msg 可根据m_suspicious中的msg模糊查询(可选)
	 * @param gameId 可根据m_suspicious中的gameId查询(可选)
	 * 
	 * */
	@GetMapping("list")
	public ReturnValue<List<MsgSuspicious>> list(HttpServletRequest request,Long uid,String msg,Long gameId) {

		AdminUser user=LoginContext.getLoginUser(request);//获取当前用户
		
		List<Long> gameIds=null;//考虑多个game分配到同一用户组下
		
		if(!user.getAccount().equals("admin")){//普通操作用户只能看自己组下的game
		
			ReturnValue<List<AdminGame>> gameVal=adminGameService.get(null,null,user.getGroupId());//根据当前用户的groupId获取gameId
			
			if(null!=gameVal.getValue()){
				
				if(gameVal.getValue().size()==1){
					gameId=gameVal.getValue().get(0).getGameId();
					
				}else{
					
					gameIds=new ArrayList<Long>();
					for(AdminGame temp : gameVal.getValue()) {
						gameIds.add(temp.getGameId());
					}
				}
			
			}else{
				return new ReturnValue<List<MsgSuspicious>>(new DefaultReturnCode(null,-1,"您所在的用户组下没有被分配产品,请联系管理员"));
			}
		}
		
		ReturnValue<List<MsgSuspicious>> returnVal=checkService.list(uid, gameId, msg,gameIds);//根据gameId获取可疑信息列表
		
		if(returnVal.getValue()==null || returnVal.getValue().size()==0){
			return new ReturnValue<List<MsgSuspicious>>(new DefaultReturnCode(null,-1,"没有查到匹配的信息"));
		}
		
		return returnVal;
	 }
	
	
	
	/**审核可疑信息
	 * 
	 * @param suspiciousId 可疑信息的标识(必填)
	 * @param illegalWords 审核员审核可疑信息违规的依据关键词(可选)
	 * 
	 * */
	@GetMapping("msgSure")
	public ReturnValue<MsgSuspicious> update(HttpServletRequest request,Long suspiciousId,String illegalWords) {

		AdminUser user=LoginContext.getLoginUser(request);//获取当前用户
		
		ReturnValue<MsgSuspicious> updateVal=checkService.msgSure(suspiciousId, illegalWords, user.getUid());
		if(updateVal.getValue().getStatus()==-1){
			userService.freeze(updateVal.getValue().getUid());//冻结用户
		}        
		
		return new ReturnValue<MsgSuspicious>();
	 }
}
