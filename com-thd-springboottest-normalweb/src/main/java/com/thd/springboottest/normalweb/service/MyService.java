package com.thd.springboottest.normalweb.service;

import com.thd.springboottest.normalweb.dto.MyBean;

/**
 * @author devil13th
 **/
public interface MyService {
    public void hello();
    public String hello(String name);
    public String hello(String name,String welcome);
    public MyBean helloSb(String name,String welcome);
}
