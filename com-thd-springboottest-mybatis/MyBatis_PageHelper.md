[TOC]

# MyBatis 分页

## 分页原理

通过MyBatis的拦截器处理分页相关的SQL

![](01.png)

## Maven依赖

```
<!-- 引入mybatis的 pagehelper 分页插件 -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
```

依赖了以下内容

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-autoconfigure</artifactId>
</dependency>
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
</dependency>
```

## 配置文件

```
pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
  params:
    count: countSql
```

## Service层

```
// 分页查询
public PageInfo<SysUser> queryByNamePage(String username, int limit, int page){
    PageHelper.startPage(page, limit).setOrderBy("user_id desc");
    PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>(sysUserMapper.queryByName(username));
    return userPageInfo;
}
```

其中 sysUserMapper.queryByName(String userName)方法就是原始的mybatis的mapper方法，不加任何分页信息。

原理：

将分页信息写到ThreadLocal中（跟踪PageHelper.startPage）

> PageHelper.startPage(page, limit).setOrderBy("user_id desc");

然后利用Mybatis的拦截器将分页的SQL加到查询SQL后面（参见顶部分页原理图）。

共计条目数是通过  select count(1) from (mybatis中生成的查询sql) 计算的。

