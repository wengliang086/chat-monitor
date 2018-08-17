package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import com.hoolai.chatmonitor.provider.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Reference
    private UserService userService;

    @Reference
    private CheckService checkService;

    @GetMapping
    public String chatMonitor(Long uid, String msg) {
        // 1、检查用户
        userService.validateUser(uid);
        // 2、分析聊天
        try {
            checkService.msgCheck(msg);
        } catch (HException e) {
            if (e.getCode() == HExceptionEnum.SENSITIVE_WORD_FIND.getCode()) {
                // 封号处理
                userService.freeze(uid);
            }
        }
        return "ok";
    }
}
