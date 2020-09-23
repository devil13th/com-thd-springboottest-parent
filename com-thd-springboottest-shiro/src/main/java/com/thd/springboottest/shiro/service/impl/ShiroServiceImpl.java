package com.thd.springboottest.shiro.service.impl;

import com.thd.springboottest.shiro.bean.ShiroPermissions;
import com.thd.springboottest.shiro.bean.ShiroRole;
import com.thd.springboottest.shiro.bean.ShiroUser;
import com.thd.springboottest.shiro.service.ShiroService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * com.thd.springboot.framework.example.service.impl.ShiroServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2020/5/7 18:58
 **/
@Service
public class ShiroServiceImpl implements ShiroService {
    @Override
    public ShiroUser loadUserByAccount(String account) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(account);
    }

    @Override
    public ShiroUser loadUserByPhone(String phone) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByPhone(phone);
    }


    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private ShiroUser getMapByName(String userName){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        ShiroPermissions permissions1 = new ShiroPermissions("1","query");
        ShiroPermissions permissions2 = new ShiroPermissions("2","add");
        ShiroPermissions permissions3 = new ShiroPermissions("2","/testPermAdd");

        Set<ShiroPermissions> permissionsSet = new HashSet<>();
        Set<ShiroPermissions> permissionsSet1 = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        permissionsSet.add(permissions3);
        permissionsSet1.add(permissions1);
        permissionsSet1.add(permissions3);


        // 角色 admin
        ShiroRole role = new ShiroRole("1","admin",permissionsSet);
        Set<ShiroRole> roleSet = new HashSet<>();
        roleSet.add(role);

        // 角色 user
        ShiroRole role1 = new ShiroRole("2","user",permissionsSet1);
        Set<ShiroRole> roleSet1 = new HashSet<>();
        roleSet1.add(role1);



        // 第一个用户 wsl 123456
        ShiroUser user = new ShiroUser("1","wsl","123456","13800138001",roleSet);
        Map<String ,ShiroUser> map = new HashMap<>();
        map.put(user.getUserName(), user);

        // 第二个用户 zhangsan 123456
        ShiroUser user1 = new ShiroUser("2","zhangsan","123456","13800138000",roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }


    /**
     * 模拟数据库查询
     * @param phone
     * @return
     */
    public ShiroUser getMapByPhone(String phone){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        ShiroPermissions permissions1 = new ShiroPermissions("1","query");
        ShiroPermissions permissions2 = new ShiroPermissions("2","add");
        Set<ShiroPermissions> permissionsSet = new HashSet<>();
        Set<ShiroPermissions> permissionsSet1 = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        permissionsSet1.add(permissions1);

        // 角色 admin
        ShiroRole role = new ShiroRole("1","admin",permissionsSet);
        Set<ShiroRole> roleSet = new HashSet<>();
        roleSet.add(role);

        // 角色 user
        ShiroRole role1 = new ShiroRole("2","user",permissionsSet1);
        Set<ShiroRole> roleSet1 = new HashSet<>();
        roleSet1.add(role1);



        // 第一个用户 wsl 123456
        ShiroUser user = new ShiroUser("1","wsl","123456","13800138001",roleSet);
        Map<String ,ShiroUser> map = new HashMap<>();
        map.put("13800138001", user);

        // 第二个用户 zhangsan 123456
        ShiroUser user1 = new ShiroUser("2","zhangsan","123456","13800138000",roleSet1);
        map.put("13800138000", user1);

        return map.get(phone);
    }
}
