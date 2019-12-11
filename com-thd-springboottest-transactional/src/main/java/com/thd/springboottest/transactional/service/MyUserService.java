package com.thd.springboottest.transactional.service;

/**
 * com.thd.springboottest.transactional.service.MyUserService
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:19
 **/
public interface MyUserService {
    public String insertAwithReqTx(String name);
    public String insertAwithNewTx(String name);
    public String insertAwithNoTx(String name);

    public String insertNestA(boolean hasEx);
    public String insertNestAGetBean(boolean hasEx);

    public void deleteAllMyUser();
}
