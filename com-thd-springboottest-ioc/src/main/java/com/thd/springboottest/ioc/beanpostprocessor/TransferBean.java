package com.thd.springboottest.ioc.beanpostprocessor;

/**
 * com.thd.springboottest.ioc.beanpostprocessor.TransferBean
 *
 * @author: wanglei62
 * @DATE: 2021/3/23 11:34
 **/
public class TransferBean implements MyInterface {

    private MyInterface echoBean;

    public TransferBean(MyInterface echoBean){
        this.echoBean = echoBean;
    }
    @Override
    public String echoSomething() {
        String str = echoBean.echoSomething();
        return this.getClass().getName() + ".echoSomething():" + str + "xxxx";
    }
}
