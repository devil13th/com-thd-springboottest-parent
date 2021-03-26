package com.thd.springboottest.applicationrunner;

import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.applicationrunner.StartIocBean
 *
 * @author: wanglei62
 * @DATE: 2021/3/24 9:55
 **/
@Component
public class StartIocBean {
    private String name;

    @Override
    public String toString() {
        return "StartIocBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
