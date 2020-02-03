package com.thd.springboottest.shiro.service;

import com.thd.springboottest.shiro.entity.Permissions;
import com.thd.springboottest.shiro.entity.Role;
import com.thd.springboottest.shiro.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.service.LoginServiceImpl
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:13
 * Description: No Description
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Override
    public User getUserByName(String getMapByName) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(getMapByName);
    }


    @Override
    public User getUserByPhone(String phone) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByPhone(phone);
    }

    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private User getMapByName(String userName){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Permissions permissions1 = new Permissions("1","query");
        Permissions permissions2 = new Permissions("2","add");
        Set<Permissions> permissionsSet = new HashSet<>();
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        permissionsSet1.add(permissions1);


        // 角色 admin
        Role role = new Role("1","admin",permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // 角色 user
        Role role1 = new Role("2","user",permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);



        // 第一个用户 wsl 123456
        User user = new User("1","wsl","123456",roleSet);
        Map<String ,User> map = new HashMap<>();
        map.put(user.getUserName(), user);

        // 第二个用户 zhangsan 123456
        User user1 = new User("2","zhangsan","123456",roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }


    /**
     * 模拟数据库查询
     * @param phone
     * @return
     */
    public User getMapByPhone(String phone){
        //共添加两个用户，两个用户都是admin一个角色，
        //wsl有query和add权限，zhangsan只有一个query权限
        Permissions permissions1 = new Permissions("1","query");
        Permissions permissions2 = new Permissions("2","add");
        Set<Permissions> permissionsSet = new HashSet<>();
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        permissionsSet1.add(permissions1);


        // 角色 admin
        Role role = new Role("1","admin",permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // 角色 user
        Role role1 = new Role("2","user",permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);



        // 第一个用户 wsl 123456
        User user = new User("1","wsl","123456","13800138001",roleSet);
        Map<String ,User> map = new HashMap<>();
        map.put("13800138001", user);

        // 第二个用户 zhangsan 123456
        User user1 = new User("2","zhangsan","123456","13800138000",roleSet1);
        map.put("13800138000", user1);

        return map.get(phone);
    }
}
