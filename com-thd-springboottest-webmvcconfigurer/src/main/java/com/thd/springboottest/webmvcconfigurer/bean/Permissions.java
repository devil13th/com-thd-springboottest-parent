package com.thd.springboottest.webmvcconfigurer.bean;

import java.io.Serializable;

/**
 * com.thd.springboottest.shiro.entity.Permissions
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:07
 * Description: No Description
 */
public class Permissions implements Serializable {
    private String id;
    private String permissionsName;

    public Permissions() {}

    public Permissions(String id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }
}
