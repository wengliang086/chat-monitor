package com.hoolai.chatmonitor.provider.process.domain.handlers;

import org.springframework.stereotype.Component;

@Component
public class PreHandler implements IPreHandler {

    @Override
    public String handle(String msg) {
        return msg.replaceAll("[*#.，,。？“”]+", "");
    }
}
