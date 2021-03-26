package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.BeanForImportBeanDefinitionRegistrar
 *
 * @author: wanglei62
 * @DATE: 2021/3/23 10:57
 **/
public class BeanForImportBeanDefinitionRegistrar {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BeanForImportBeanDefinitionRegistrar{" +
                "message='" + message + '\'' +
                '}';
    }
}
