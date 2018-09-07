package com.hoolai.chatmonitor.provider.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TestConfig {

    @Value("${config.variables.env}")
    private String env;

    public void print() {

    }
}
