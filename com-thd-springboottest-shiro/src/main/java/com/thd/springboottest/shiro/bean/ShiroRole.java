package com.thd.springboottest.shiro.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.entity.Role
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:06
 * Description: No Description
 */
public class ShiroRole implements Serializable {
    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<ShiroPermissions> permissions;

    public ShiroRole(String id, String roleName, Set<ShiroPermissions> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<ShiroPermissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<ShiroPermissions> permissions) {
        this.permissions = permissions;
    }
}
