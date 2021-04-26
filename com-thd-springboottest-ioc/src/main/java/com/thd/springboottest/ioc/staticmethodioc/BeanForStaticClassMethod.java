package com.thd.springboottest.ioc.staticmethodioc;

import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.ioc.staticmethodioc.BeanForStaticClassMethod
 *
 * @author: wanglei62
 * @DATE: 2021/4/26 17:23
 **/
@Component(value="beanForStaticClassMethod")
public class BeanForStaticClassMethod {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeanForStaticClassMethod{" +
                "name='" + name + '\'' +
                '}';
    }
}
