package com.thd.springboottest.ioc.bean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * com.thd.springboottest.ioc.bean.MyImportSelector
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 17:42
 **/
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        // annotationMetadata 参数是springBoot启动类中的注解
        return new String[]{BeanForImportSelector.class.getName()};
    }
}
