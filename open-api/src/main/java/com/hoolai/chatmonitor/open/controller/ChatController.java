package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.dao.AdminGameDao;
import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminGame;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import com.hoolai.chatmonitor.provider.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "聊天内容检查控制器")
@RestController
@RequestMapping("chat")
public class ChatController {

    @Reference
    private UserService userService;

    @Reference
    private CheckService checkService;

    @Resource
    private AdminGameDao adminGameDao;

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "uid", value = "hoolai uid", paramType = "Long"),
//            @ApiImplicitParam(name = "msg", value = "聊天内容", paramType = "String")
//    })
    @ApiResponses({
            @ApiResponse(code = 2, message = "UID 不存在"),
            @ApiResponse(code = 3, message = "存在敏感词汇"),
            @ApiResponse(code = 4, message = "该用户已经被冻结")
    })
    @ApiOperation(value = "聊天内容检查", notes = "备注：游戏根据返回码决定该消息是否继续发送")
    @GetMapping
    public String chatMonitor(long gameId, String gameUid, String msg, String accessToken, long requestTime) {
        validateToken(gameId, accessToken, requestTime);
        // 1、检查用户
        Long uid = userService.validateUser(gameId, gameUid);
        // 2、分析聊天
        try {
            checkService.msgCheck(uid, gameId, gameUid, msg);
        } catch (HException e) {
            if (e.getCode() == HExceptionEnum.SENSITIVE_WORD_FIND.getCode()) {
                // 封号处理
                userService.freeze(uid);
            }
            throw e;
        }
        return "ok";
    }

    private void validateToken(long gameId, String accessToken, long requestTime) {
        AdminGame adminGame = adminGameDao.get(gameId);
        if (adminGame == null) {
            throw HException.HExceptionBuilder.newBuilder(HExceptionEnum.GAME_NOT_IEXIST).build();
        }
        String gameKey = adminGame.getGameKey();
        String token = DigestUtils.md5Hex(gameId + "." + requestTime + "." + gameKey);
        if (!token.equals(accessToken)) {
            throw HException.HExceptionBuilder.newBuilder(HExceptionEnum.VALIDATE_ACCESS_TOKEN_FAILED).build();
        }
    }
}
