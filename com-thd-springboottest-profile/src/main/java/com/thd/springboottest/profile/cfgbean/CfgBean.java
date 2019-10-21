package com.thd.springboottest.profile.cfgbean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboot.profile.cfgbean.CfgBean
 *
 * @author: wanglei62
 * @DATE: 2019/10/20 23:21
 **/
@Component
@ConfigurationProperties(prefix="cfg-bean")
public class CfgBean {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CfgBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

