# SpringBoot 命令行指定参数或配置文件

> 将SpringBoot应用打jar后使用命令行 java -jar命令启动应用，在启动应用时通常会指定一些配置参数或配置文件位置，本文讲解如何指定这些设置



# 本例代码

## 代码目录

![](01.png)

## 配置类

```
@Component
@ConfigurationProperties(value="cfg")
public class Cfg {
    private CfgBean cfgBean;
    private String process;
    private String appName;
    private String filePath;
    ... setter getter 
}
```



```
public class CfgBean {
    private String name;
    private Integer age;
    ... setter getter
}
```

## Controller

```
@Controller
@RequestMapping(value="/config")
public class TestController {
    @Autowired
    private Cfg cfg ;

    @ResponseBody
    @RequestMapping(value="/test")
    public ResponseEntity test(){
        System.out.println("test()");
        System.out.println(cfg);
        return ResponseEntity.ok(cfg);
    }
}

```



## 启动类

```
@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@EnableConfigurationProperties({Cfg.class})
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] names = ctx.getBeanDefinitionNames();
    }
}
```

## 配置文件

application.yml

```
spring:
  profiles:
    # 开启生效的配置文件 (spring.profiles对应的配置以及 application-{xxx}.yml 都会同时生效)
    # //打包后 java -jar 名称.jar --spring.profiles.active=xxx 来控制使用哪套配置
    # 可以用下面三个命令启动
    # java -jar com-thd-springboottest-profile-1.0-SNAPSHOT.jar --spring.profiles.active=dev
    # java -jar com-thd-springboottest-profile-1.0-SNAPSHOT.jar --spring.profiles.active=test
    # java -jar com-thd-springboottest-profile-1.0-SNAPSHOT.jar --spring.profiles.active=prod
    #active: prod  # 开启生产环境配置
    active: test    # 开启测试环境配置
    #active: dev     # 开启开发环境配置


# 不同环境的配置，使用 --- 进行分割，以spring.profiles属性作为标识
---
#dev环境 配置
spring:
  profiles: dev
server:
  port: 8880
  servlet:
    context-path: /dev
---
#test 环境 配置
spring:
  profiles: test
server:
  port: 8881
  servlet:
    context-path: /test
---
#prod 环境 配置
spring:
  profiles: prod
server:
  port: 8882
  servlet:
    context-path: /prod

```
application-dev.yml
```
cfg:
  appName: dev
  process: DEV
  filePath: D://dev
  cfgBean:
    name: dev
    age: 1


```
application-prod.yml
```
cfg:
  appName: prod
  process: PROD
  filePath: D://prod
  cfgBean:
    name: prod
    age: 2

```
application-test.yml
```
cfg:
  appName: test
  process: TEST
  filePath: D://test
  cfgBean:
    name: test
    age: 3

```



# 使用jar中的配置文件作为配置

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar
```

则该应用使用jar包中的配置文件



# 命令行指定某个配置文件中的参数值

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --server.port=8000
```

如果命令行指定了参数则会优先于jar包中配置文件的配置



# 命令行指定spring.profiles.active

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --spring.profiles.active=prod
```

会激活指定的环境下的配置，application.yml中的spring.profiles.active配置会失效，以命令行的参数为准



# 同时指定spring.profiles.active和配置文件中的参数值

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --spring.profiles.active=dev --server.port=8000 --cfg.cfgBean.name=xxx
```

application-dev.yml中的cfg.cfgBean.name失效，以命令行的参数为准，端口号配置项也是

# 配置文件外置，通过命令行指定

将application.yml、application-dev.yml、application-prod.yml、application-test.yml这四个文件放到jar包所在目录下，然后执行以下代码

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --spring.config.location=application.yml,application-dev.yml,application-prod.yml,application-test.yml
```

通过spring.config.location可以指定外置的配置文件，被指定的配置文件优先级高于jar包内部的配置文件，如果指定的配置文件是相对位置，则为jar所在目录的相对位置。如果为决定位置，则命令如下

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --spring.config.location=D:\deleteme\application.yml,D:\deleteme\application-dev.yml,application-prod.yml,D:\deleteme\application-test.yml
```



# 外置配置文件中的spring.profiles.active

指定了外置的配置文件，可以让多个配置文件中的某一个配置生效，如上例子

```
java -jar com-thd-springboottest-config-1.0-SNAPSHOT.jar --spring.config.location=D:\deleteme\application.yml,D:\deleteme\application-dev.yml,application-prod.yml,D:\deleteme\application-test.yml
```

