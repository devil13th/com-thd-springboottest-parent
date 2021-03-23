package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.BeanForImport
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 17:52
 **/
public class BeanForAnnotationImport {
    private String message;

    @Override
    public String toString() {
        return "BeanForAnnotationImport{" +
                "message='" + message + '\'' +
                '}';
    }
}
