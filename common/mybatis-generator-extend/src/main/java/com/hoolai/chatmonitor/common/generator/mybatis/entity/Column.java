package com.hoolai.chatmonitor.common.generator.mybatis.entity;

import com.hoolai.chatmonitor.common.generator.mybatis.test.util.Util;

public class Column {

    private String name;

    private Class type;

    public Column() {
    }

    public Column(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getJavaProperty() {
        return Util.getJavaProperty(this.getName());
    }


}
