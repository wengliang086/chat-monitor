package com.hoolai.chatmonitor.open.controller;

import com.hoolai.chatmonitor.open.aspect.AuthAspect;
import com.hoolai.chatmonitor.open.auth.LoginContext;
import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminUser;
import com.hoolai.chatmonitor.open.log.OperateLog;
import com.hoolai.chatmonitor.open.model.UserLoginResponse;
import com.hoolai.chatmonitor.open.service.AdminGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@PermissionAnnotation(PermissionType.LOGINED)
@RestController
@RequestMapping("game")
public class AdminGameController {

    @Resource
    private AdminGameService adminGameService;

    @OperateLog(value = "创建游戏", pKey = "gameId")
    @GetMapping("add")
    public AdminGame register(HttpServletRequest request, String name, Integer groupId) {
        return adminGameService.add(name, groupId);
    }

    @OperateLog(value = "修改游戏", pKey = "gameId")
    @GetMapping("update")
    public AdminGame updateUserInfo(HttpServletRequest request, Long gameId, String name, Integer groupId) {
        return adminGameService.update(gameId, name, groupId);
    }

    /**
     * 当前用户所属组下的game列表
     *
     * @param name gameName (可选)
     */
    @GetMapping("list")
    public List<AdminGame> list(HttpServletRequest request, String name) {
        UserLoginResponse user = AuthAspect.get(request);
        Integer groupId = 0;
        if (user != null) {
            groupId = user.getGroupId();
        }
        return adminGameService.get(null, name, groupId);
    }

}
