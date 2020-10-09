package com.thd.springboottest.annotation.ipt;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

public class MyBeanRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        System.out.println("============== MyBeanRegister Start ===============");
        annotationMetadata.getAnnotationTypes().stream().forEach(System.out::println);
        System.out.println("----------------------");
        Arrays.stream(beanDefinitionRegistry.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("============== MyBeanRegister End ===============");
    }
}
