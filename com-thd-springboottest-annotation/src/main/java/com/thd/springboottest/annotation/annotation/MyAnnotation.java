package com.thd.springboottest.annotation.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * com.thd.springboottest.annotation.annotation.MyAnnotation
 *
 * @author: wanglei62
 * @DATE: 2019/9/30 15:30
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication(scanBasePackages = "com.thd.springboottest")

public @interface MyAnnotation {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String[] names() default {};

}
