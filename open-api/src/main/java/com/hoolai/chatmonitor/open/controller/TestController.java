package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.process.service.DemoService;
import io.swagger.annotations.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "测试控制器")
@RestController
@RequestMapping("test")
public class TestController {

    @Reference
    private DemoService demoService;

    @ApiOperation(value = "测试服务是否正常", notes = "备注：没有参数")
    @GetMapping("ok")
    public String ok() {
        return "open-api: ok";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "说话者名称", dataType = "String", paramType = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 402, message = "返回说话内容111")
    })
    @ApiOperation(value = "测试Dubbo跨项目访问是否正常", notes = "备注：测试Dubbo跨项目访问是否正常")
    @GetMapping("hello")
    public String hello(String name) {
        return demoService.sayHello(name);
    }

    @GetMapping("exception")
    public String exception(int i) throws Throwable {
        switch (i) {
            case 0:
                throw HException.HExceptionBuilder.newBuilder(HExceptionEnum.UID_ALREADY_FREEZED).build();
            case 1:
                throw new RuntimeException("测试运行时异常");
            case 2:
                throw new IOException("测试Exception异常");
            case 3:
                throw new Error("测试Error错误");
        }
        return "";
    }

    public static void main(String[] args) {
        String[] split = "/test_a/oknul.hl".split("[/.]");
        for (String s : split) {
            System.out.println(s);
        }
        long gameId = 1009;
        long requestTime = System.currentTimeMillis();
        String gameKey = "ZTEZX2A32svw24i6";
        String token = DigestUtils.md5Hex(gameId + "." + requestTime + "." + gameKey);
        System.out.println("requestTime=" + requestTime + "&accessToken=" + token);
    }
}
