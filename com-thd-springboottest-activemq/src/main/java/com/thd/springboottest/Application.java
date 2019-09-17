package com.thd.springboottest;

import com.thd.springboottest.activemq.bean.MqCfgBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@Import(MqCfgBean.class)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }


    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println(" -------------- IOC BEAN ------------------");
        String[] names = ctx.getBeanDefinitionNames();
        for(String name : names){
            System.out.println(name);
        }
        System.out.println(" ------------ 配置 --------------- ");
        ConfigurableEnvironment env = ctx.getEnvironment();
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }

        System.out.println(" ------------ URL Mapping --------------- ");
        RequestMappingHandlerMapping mapping = ctx.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		List<String> urlList = new ArrayList<>();
		for (RequestMappingInfo info : map.keySet()) {
			// 获取url的Set集合，一个方法可能对应多个url
			Set<String> patterns = info.getPatternsCondition().getPatterns();

			for (String url : patterns) {
			    System.out.println(url);
				urlList.add(url);
			}
		}

    }




}
