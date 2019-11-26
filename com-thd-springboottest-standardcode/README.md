[TOC]
# SpringBoot MVC+mybatis 集成标准代码

> 创建项目的标准例子

# Feature
- Spring MVC
- MyBatis
## druid 数据源连接池
1. application.yml 中 spring.datasource.druid 配置数据源属性
2. configuration/DruidConfiguration 将druid数据源纳入到ioc容器中
3. 监控地址
http://127.0.0.1:8899/thd/druid/index.html

## jdbc
1. application.yml中配置数据源spring.datasource(也用于mybatis)
2. dao/JdbcDao jdbc接口
3. dao/JdbcDaoImpl jdbc接口实现
4. bean/Page 分页bean
5. controller/JdbcController jdbc测试controller

## redis
1. pom.xml
redis依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!-- jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```
2. configuration/RedisConfiguration
向IOC注册Redis相关Bean以及设置序列化时使用的序列化工具

3. application.yml
spring.redis 配置了连接信息和连接池信息

4. utils/FastJsonRedisSerializer
redis对象序列化工具使用fastjson

5. entity/MyUser
使用@JSONField对Date和Timestamp进行格式化设置,以便fastjson按照设置的格式进行序列化和反序列化
```
// redis使用fastjson格式化
@JSONField(format="yyyy-MM-dd")
private Date userBirthday;

// redis使用fastjson格式化
@JSONField(format="yyyy-MM-dd HH:mm:ss")
private Timestamp userCreateTime;
```


## Lombok 
1. pom.xml
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
2. entity/MyUser 中添加了@Data 就可以省略setter getter
## swagger 
## logback
1. logback-spring.xml中配置了日志规则和格式以及输出目的地
2. application.yml 中logging配置
## 时间戳处理
Date统一使用yyyy-MM-dd Timestamp使用yyyy-MM-dd HH:mm:ss
1. application.yml 
spring.jackson 中配置了Date类型的序列化格式(服务端 -> 客户端)
2. entity/MyUser
@DateTimeFormat(pattern="yyyy-MM-dd") 配置Date类型从客户端转到服务端的格式
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 配置Timestame的转换格式(服务端到客户端以及客户端到服务端)




## 拦截器
1. configuration/InterceptorConfig 使用拦截器的配置
2. configuration/WebInterceptor 定义拦截器

## 全局异常定义及处理
1. advice/MVCExceptionHandler 全局异常处理切面
2. bean/MessageEnum 定义了异常信息枚举
3. bean/Result 定义了http访问的统一格式bean
4. exceptiondefine/MyParentException 定义了全局统一异常父类
5. exceptiondefine/CustomException 定义了一个自定义的异常,用于例子
6. controller/ExceptionHandlerController 测试controller
异常返回结果测试地址：http://127.0.0.1:8899/thd/exceptionhandler/test/error
正常返回结果测试地址：http://127.0.0.1:8899/thd/exceptionhandler/test/error

## thymeleaf
1. pom.xml
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
2. application.yml 中spring.thymeleaf配置
3. resources/static 目录用于放置thymeleaf模板文件
4. controller/ThymeleafController 用于测试thymeleaf


## profiles功能

## mybatis 日志输出
1. logback-spring.xml中的root的级别改为debug
2. logback-spring.xml中的CONSOLE_COLOR的级别改为debug
