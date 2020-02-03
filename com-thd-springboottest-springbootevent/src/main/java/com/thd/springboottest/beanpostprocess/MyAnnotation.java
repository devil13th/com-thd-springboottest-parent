package com.thd.springboottest.beanpostprocess;

import java.lang.annotation.*;

/**
 * com.thd.springboottest.annotation.MyAnnotation
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 16:32
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {

}
