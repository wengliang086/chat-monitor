package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException.HExceptionBuilder;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.aspect.AuthAspect;
import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.log.OperateLog;
import com.hoolai.chatmonitor.open.model.UserLoginResponse;
import com.hoolai.chatmonitor.open.service.AdminGameService;
import com.hoolai.chatmonitor.open.service.AdminGroupService;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import com.hoolai.chatmonitor.provider.user.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@PermissionAnnotation(PermissionType.LOGINED)
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

    /**
     * 查询可疑信息列表
     *
     * @param uid    可根据u_login_info中的uid查询 (可选)
     * @param msg    可根据m_suspicious中的msg模糊查询(可选)
     * @param gameId 可根据m_suspicious中的gameId查询(可选)
     */
    @GetMapping("list")
    public List<MsgSuspicious> list(HttpServletRequest request, Long uid, String msg, Long gameId) {
        UserLoginResponse user = AuthAspect.get(request);;//获取当前用户

        List<Long> gameIds = null;//考虑多个game分配到同一用户组下

        if (!user.getAccount().equals("admin")) {//普通操作用户只能看自己组下的game

            List<AdminGame> gameList = adminGameService.get(null, null, user.getGroupId());//根据当前用户的groupId获取gameId

            if (null != gameList) {

                if (gameList.size() == 1) {
                    gameId = gameList.get(0).getGameId();
                } else {
                    gameIds = new ArrayList<Long>();
                    for (AdminGame temp : gameList) {
                        gameIds.add(temp.getGameId());
                    }
                }
            } else {
                throw HExceptionBuilder.newBuilder(HExceptionEnum.GROUP_NOT_GAME).build();
            }
        }

        List<MsgSuspicious> re= checkService.list(uid, gameId, msg, gameIds);//根据gameId获取可疑信息列表
        return re;
    }

    /**
     * 查询详细的可疑信息列表（例如包括game_name、operator、status说明。。）
     *
     * @param account  可根据admin_user中的account查询 (可选)
     * @param gameName 可根据admin_game中的game_name查询(可选)
     * @param msg      可根据m_suspicious中的msg模糊查询(可选)
     * @param status   可根据m_suspicious中的status查询(可选)
     * @param groupId  可根据admin_game中的group_id查询(可选)非admin用户只能查询自己group下的游戏的可疑信息
     * @param gameId   可根据m_suspicious中的game_id查询(可选)
     */
    @GetMapping("listDetail")
    public List<com.hoolai.chatmonitor.provider.process.client.vo.MsgSuspicious> listDetail(HttpServletRequest request, String account, String gameName, Byte status, Integer gameId, Integer groupId, String msg) {

        UserLoginResponse user = AuthAspect.get(request);//获取当前用户
		
		if(!user.getAccount().equals("admin")){//普通操作用户只能看自己组下的game的可疑信息
			groupId=user.getGroupId();
		}

        return checkService.selectSuspiciousMapList(account, gameName, msg, status, gameId, groupId);
    }

    /**
     * 审核可疑信息
     *
     * @param suspiciousId 可疑信息的标识(必填)
     * @param illegalWords 审核员审核可疑信息违规的依据关键词(可选)
     */
    @OperateLog(value = "审核可疑信息", pKey = "suspiciousId")
    @GetMapping("msgSure")
    public MsgSuspicious update(HttpServletRequest request, Long suspiciousId, String illegalWords) {
        UserLoginResponse user = AuthAspect.get(request);//获取当前用户
        MsgSuspicious updateVal = checkService.msgSure(suspiciousId, illegalWords, user.getUid());
        if (updateVal.getStatus() == -1) {
            userService.freeze(updateVal.getUid());//冻结用户
        }
        return updateVal;
    }
}
