package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import com.hoolai.chatmonitor.provider.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "聊天内容检查控制器")
@RestController
@RequestMapping("chat")
public class ChatController {

    @Reference
    private UserService userService;

    @Reference
    private CheckService checkService;

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
    public String chatMonitor(long gameId, String gameUid, String msg) {
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
}
