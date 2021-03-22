package com.thd.springboottest.ioc.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.ioc.bean.MyFactoryBean
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 13:51
 **/
@Component
public class MyFactoryBean implements FactoryBean<BeanForFactoryBean> {
    @Value("${message}")
    private String message;
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public BeanForFactoryBean getObject() throws Exception {
        BeanForFactoryBean m =  new BeanForFactoryBean();
        m.setMessage(message);
        return m;
    }

    @Override
    public Class<?> getObjectType() {
        return BeanForFactoryBean.class;
    }
}
