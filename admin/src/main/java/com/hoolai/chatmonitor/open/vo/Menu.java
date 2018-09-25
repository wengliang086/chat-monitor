package com.hoolai.chatmonitor.open.vo;

import com.hoolai.chatmonitor.open.dao.mybatis.vo.AdminPermission;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private String path;
    private String component;
    private String name;
    private String iconCls;
    private List<Menu> children;
    private String redirect;// 简单起见，先用String，如 { path: "/game" }

    public Menu(AdminPermission permission) {
        this.path = permission.getPath();
        this.component = permission.getComponent();
        this.name = permission.getName();
        this.iconCls = permission.getIcon();
    }

    public void addChildren(Menu children) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
