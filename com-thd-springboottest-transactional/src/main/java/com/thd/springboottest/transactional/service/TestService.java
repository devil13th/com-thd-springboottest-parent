package com.thd.springboottest.transactional.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * com.thd.springboottest.transactional.service.TestService
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:31
 **/
public interface TestService {
    /**
     * testService 带有required类型的事务
     *    |- myUserService 带有required类型的事务
     *    |- 抛出异常
     *    |- sysUserService带有required类型的事务
     *
     *    全部没有插入成功,事务达到预期
     */
    public void insertA(boolean hasEx);
    /**
     * testService 没有事务
     *     |- myUserService 带有required类型的事务
     *     |- 抛出异常
     *     |- sysUserService带有required类型的事务
     *
     *     myUserService插入成功 sysUserService插入失败  事务未达到预期
     */
    public void insertB(boolean hasEx);
    /**
     * testService 带有required类型的事务
     *     |- myUserService 没有事务
     *     |- 抛出异常
     *     |- sysUserService 没有事务
     *     全部没有插入成功，事务达到预期
     */
    public void insertC(boolean hasEx);
    /**
     * testService 带有required类型的事务
     *     |- myUserService 带有required类型的事务
     *     |- sysUserService 带有requires_new类型的事务
     *     |- 抛出异常
     *     myUserService插入失败 sysUserService插入成功，事务达到预期
     */
    public void insertD(boolean hasEx);
    /**
     *
     */
    public void insertE(boolean hasEx);

    /**
     *
     */
    public void insertF(boolean hasEx);


    //删除my_user sys_user所有数据
    public void deleteAll();
}
