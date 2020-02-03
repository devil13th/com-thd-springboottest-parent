package com.thd.springboottest.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class MyListener implements SessionListener {
    @Override
    // 会话创建触发
    public void onStart(Session session) {
        System.out.println("session start");
    }

    @Override
    // 退出会话时触发
    public void onStop(Session session) {

        System.out.println("session stop");
    }

    @Override
    // 会话过期时触发
    public void onExpiration(Session session) {
        System.out.println("session expiration");
    }
}
