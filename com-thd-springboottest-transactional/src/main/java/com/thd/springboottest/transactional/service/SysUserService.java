package com.thd.springboottest.transactional.service;

/**
 * com.thd.springboottest.transactional.service.SysUserService
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:19
 **/
public interface SysUserService {
    public String insertBwithReqTx(String name);
    public String insertBwithNewTx(String name);
    public String insertBwithNoTx(String name);
    public void deleteAllSysUser();
}
