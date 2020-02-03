package com.thd.springboottest.fastjson.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.entity.User
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:05
 * Description: No Description
 */
public class User implements Serializable {

    /**
     * @JSONField 注释属性含义：
     *
     * public @interface JSONField {
     *     // 配置序列化和反序列化的顺序，1.1.42版本之后才支持
     *     int ordinal() default 0;
     *
     *      // 指定字段的名称
     *     String name() default "";
     *
     *     // 指定字段的格式，对日期格式有用
     *     String format() default "";
     *
     *     // 是否序列化
     *     boolean serialize() default true;
     *
     *     // 是否反序列化
     *     boolean deserialize() default true;
     * }
     */


    /**
     * 反序列化可自动识别的日期格式
     * ISO-8601日期格式
     * yyyy-MM-dd
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss.SSS
     * 毫秒数字
     * 毫秒数字字符串
     * .NET JSON日期格式
     * new Date(198293238)
     */
    @JSONField(ordinal=1,name="userId")
    private String id;
    @JSONField(ordinal=2)
    private String userName;
    @JSONField(serialize=false)
    private String password;
    @JSONField(ordinal=3)
    private String phone;
    @JSONField(ordinal=4,format="yyyy-MM-dd")
    private Date birthday;
    @JSONField(ordinal=5,deserialize=false)
    private int age;




    /**
     * 用户对应的角色集合
     */
    @JSONField(ordinal=6,deserialize=false)
    private Set<Role> roles;

    public User(){}

    public User(String id, String userName, String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public User(String id, String userName, String password, String phone,Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
