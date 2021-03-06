package com.thd.springboottest.fastjson.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.entity.Role
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:06
 * Description: No Description
 */
public class Role  implements Serializable {
    @JSONField(ordinal = 1)
    private String id;
    @JSONField(ordinal = 2)
    private String roleName;
    /**
     * 角色对应权限集合
     */
    @JSONField(ordinal = 3)
    private Set<Permissions> permissions;
    public Role(String id, String roleName, Set<Permissions> permissions) {
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

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
