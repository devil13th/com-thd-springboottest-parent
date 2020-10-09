package com.thd.springboottest.annotation.ipt;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        System.out.println("============== MyImportSelector ===============");
        System.out.println(annotationMetadata.getClassName().toString());
        annotationMetadata.getAnnotationTypes().stream().forEach(System.out::println);
        return new String[]{"com.thd.springboottest.annotation.ipt.MyImportBeanC","com.thd.springboottest.annotation.ipt.MyImportBeanD"};
    }
}
