[TOC]
# SpringBoot MVC+mybatis 集成标准代码

> 创建项目的标准例子

Feature : 
- Spring MVC
- MyBatis
- druid 数据源连接池
- redis
- Lombok 
- swagger 
- logback
  logback-spring.xml
  application.yml 中logging配置
- 时间戳处理
- 拦截器
  interceptorConfig.java
  WebInterceptor
  
- RestFul
- thymeleaf
- profiles功能

### mybatis 日志输出
1. logback-spring.xml中的root的级别改为debug
2. logback-spring.xml中的CONSOLE_COLOR的级别改为debug
