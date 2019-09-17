package com.thd.springboottest.optimize.service;


import com.thd.springboottest.optimize.dto.MyBean;

/**
 * @author devil13th
 **/
public interface MyService {
    public void hello();
    public String hello(String name);
    public String hello(String name, String welcome);
    public MyBean helloSb(String name, String welcome);

    /**
     * 测试栈溢出
     * @param i
     */
    public void stackOver(int i);
}
