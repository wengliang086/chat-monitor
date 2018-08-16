package com.hoolai.chatmonitor.provider.process.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.provider.process.service.CheckService;
import org.apache.logging.log4j.util.Strings;

@Service(timeout = 10000, retries = 0)
public class CheckServiceImpl implements CheckService {
    @Override
    public void msgCheck(String msg) {
        if (Strings.isEmpty(msg)) {
            return;
        }
        if (msg.contains("a")) {
            throw new HException(HExceptionEnum.SENSITIVE_WORD_FIND);
        }
    }
}
