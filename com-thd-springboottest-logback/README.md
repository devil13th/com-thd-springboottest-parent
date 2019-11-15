# SpringBoot 集成 LogBack

[Logback](http://logback.qos.ch/) 是log4j 框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J
Logback的定制性更加灵活，同时也是spring boot的内置日志框架

## 添加依赖

SpringBoot默认使用的就是LogBack所以不用引用任何依赖，分析如下：



springboot的pom文件都会引一个parent

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
</parent>
```

点进去这个parent，会有一个这个dependency

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.0.0.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

再点进去就是2.0版本，所谓的它给你集成的各种包依赖，而且规定了版本号，其中有一个包如下

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
```

再点进去

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
    <version>2.0.0.RELEASE</version>
    <scope>compile</scope>
</dependency>
```

再点，下面这些都是原有的日志包，所以，不必再引依赖了，直接用就ok了

```
<dependencies>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-to-slf4j</artifactId>
      <version>2.10.0</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>jul-to-slf4j</artifactId>
      <version>1.7.25</version>
      <scope>compile</scope>
</dependency>
```
##  **Logger, Appenders and Layouts** 

在讲解 log'back-spring.xml之前我们先来了解三个单词：Logger, Appenders and Layouts（记录器、附加器、布局）：Logback基于三个主要类：Logger，Appender和Layout。 这三种类型的组件协同工作，使开发人员能够根据消息类型和级别记录消息，并在运行时控制这些消息的格式以及报告的位置。首先给出一个基本的xml配置如下

```

<configuration>
 
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <!-- encoders are assigned the type
         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
 
  <logger name="chapters.configuration" level="INFO"/>
 
  <!-- Strictly speaking, the level attribute is not necessary since -->
  <!-- the level of the root level is set to DEBUG by default.       -->
  <root level="DEBUG">          
    <appender-ref ref="STDOUT" />
  </root>  
  
</configuration>

```

###  `<configuration>`元素

 logback.xml配置文件的基本结构可以描述为`<configuration>`元素，包含零个或多个`<appender>`元素，后跟零个或多个`<logger>`元素，后跟最多一个`<root>`元素(也可以没有)。下图说明了这种基本结构： 

![](05.png)


### `<logger>`元素

`<logger>`元素只接受一个必需的name属性，一个可选的level属性和一个可选的additivity属性，允许值为true或false。 level属性的值允许一个不区分大小写的字符串值TRACE，DEBUG，INFO，WARN，ERROR，ALL或OFF。特殊于大小写不敏感的值INHERITED或其同义词NULL将强制记录器的级别从层次结构中的较高级别继承，`<logger>`元素可以包含零个或多个`<appender-ref>`元素; 这样引用的每个appender都被添加到指定的logger中，(注：additivity属性下面详说)，logger元素级别具有继承性。

例1：示例中，仅为根记录器分配了级别。 此级别值DEBUG由其他记录器X，X.Y和X.Y.Z继承

![](01.png)
例2：所有记录器都有一个指定的级别值。 级别继承不起作用

![](02.png)
例3：记录器root，X和X.Y.Z分别被分配了DEBUG，INFO和ERROR级别。 Logger X.Y从其父X继承其级别值。

![](03.png)
例4：在示例4中，记录器root和X分别被分配了DEBUG和INFO级别。 记录器X.Y和X.Y.Z从其最近的父X继承其级别值，该父级具有指定的级别。

![](04.png)



### `<root>`元素

`<root>`元素配置根记录器。 它支持单个属性，即level属性。 它不允许任何其他属性，因为additivity标志不适用于根记录器。 此外，由于根记录器已被命名为“ROOT”，因此它也不允许使用name属性。 level属性的值可以是不区分大小写的字符串TRACE，DEBUG，INFO，WARN，ERROR，ALL或OFF之一`<root>`元素可以包含零个或多个`<appender-ref>`元素; 这样引用的每个appender都被添加到根记录器中(注：additivity属性下面详说)。

### `<appender>`元素

appender使用`<appender>`元素配置，该元素采用两个必需属性name和class。 name属性指定appender的名称，而class属性指定要实例化的appender类的完全限定名称。 `<appender>`元素可以包含零个或一个`<layout>`元素，零个或多个`<encoder>`元素以及零个或多个`<filter>`元素，下图说明了常见的结构：

![](06.png)



```
重要：在logback中，输出目标称为appender，addAppender方法将appender添加到给定的记录器logger。给定记录器的每个启用的日志记录请求都将转发到该记录器中的所有appender以及层次结构中较高的appender。换句话说，appender是从记录器层次结构中附加地继承的。例如，如果将控制台appender添加到根记录器，则所有启用的日志记录请求将至少在控制台上打印。如果另外将文件追加器添加到记录器（例如L），则对L和L的子项启用的记录请求将打印在文件和控制台上。通过将记录器的additivity标志设置为false，可以覆盖此默认行为，以便不再添加appender累积。

```

 Appender是一个接口，它有许多子接口和实现类，具体如下图所示： 

![](07.jpg)

 其中最重要的两个Appender为：**ConsoleAppender 、RollingFileAppender。** 

#### ConsoleAppender

ConsoleAppender，如名称所示，将日志输出到控制台上。

#### RollingFileAppender

RollingFileAppender，是FileAppender的一个子类，扩展了FileAppender，具有翻转日志文件的功能。 例如，RollingFileAppender 可以记录到名为log.txt文件的文件，并且一旦满足某个条件，就将其日志记录目标更改为另一个文件。

有两个与RollingFileAppender交互的重要子组件。 第一个RollingFileAppender子组件，即 RollingPolicy 负责执行翻转所需的操作。 RollingFileAppender的第二个子组件，即 TriggeringPolicy 将确定是否以及何时发生翻转。 因此，RollingPolicy 负责什么和TriggeringPolicy 负责什么时候。

作为任何用途，RollingFileAppender 必须同时设置 RollingPolicy 和 TriggeringPolicy。 但是，如果其 RollingPolicy 也实现了TriggeringPolicy 接口，则只需要显式指定前者。

#### 滚动策略

TimeBasedRollingPolicy：可能是最受欢迎的滚动策略。 它根据时间定义翻转策略，例如按天或按月。 TimeBasedRollingPolicy承担滚动和触发所述翻转的责任。 实际上，TimeBasedTriggeringPolicy实现了RollingPolicy和TriggeringPolicy接口。

SizeAndTimeBasedRollingPolicy：有时您可能希望按日期归档文件，但同时限制每个日志文件的大小，特别是如果后处理工具对日志文件施加大小限制。 为了满足此要求，logback 提供了 SizeAndTimeBasedRollingPolicy ，它是TimeBasedRollingPolicy的一个子类，实现了基于时间和日志文件大小的翻滚策略。


#### `<encoder>`元素

  encoder中最重要就是pattern属性，它负责控制输出日志的格式，这里给出一个我自己写的示例： 

```
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) --- [%15.15(%thread)] %cyan(%-40.40(%logger{40})) : %msg%n</pattern>
```

![](08.png)

其中：
```
%d{yyyy-MM-dd HH:mm:ss.SSS}：日期

%-5level：日志级别    

%highlight()：颜色，info为蓝色，warn为浅红，error为加粗红，debug为黑色    

%thread：打印日志的线程    

%15.15():如果记录的线程字符长度小于15(第一个)则用空格在左侧补齐,如果字符长度大于15(第二个),则从开头开始截断多余的字符  

%logger：日志输出的类名    

%-40.40()：如果记录的logger字符长度小于40(第一个)则用空格在右侧补齐,如果字符长度大于40(第二个),则从开头开始截断多余的字符   

%cyan：颜色    

%msg：日志输出内容    

%n：换行符

```

#### `<filter>`元素

 filter中最重要的两个过滤器为：**LevelFilter**、**ThresholdFilter。** 

LevelFilter 根据精确的级别匹配过滤事件。 如果事件的级别等于配置的级别，则筛选器接受或拒绝该事件，具体取决于onMatch和onMismatch属性的配置。 例如下面配置将只打印INFO级别的日志，其余的全部禁止打印输出：


```
<configuration>
  <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <filter class="ch.qos.logback.classic.filter.LevelFilter">
<!--
onMismatch（匹配不成功） 有2个值： ACCEPT 和 DENY
输出的级别等于 level 标签里的 级别时：
如果值 = ACCEPT ，则输出
如果值 = DENY , 不输出
-->
      <level>INFO</level>
      <onMatch>ACCEPT</onMatch>
      <onMismatch>DENY</onMismatch>
    </filter>
    <encoder>
      <pattern>
        %-4relative [%thread] %-5level %logger{30} - %msg%n
      </pattern>
    </encoder>
  </appender>
  <root level="DEBUG">
    <appender-ref ref="CONSOLE" />
  </root>
</configuration>
```



ThresholdFilter 过滤低于指定阈值的事件。 对于等于或高于阈值的事件，ThresholdFilter将在调用其decision（）方法时响应NEUTRAL。 但是，将拒绝级别低于阈值的事件，例如下面的配置将拒绝所有低于INFO级别的日志，只输出INFO以及以上级别的日志：

```
<configuration>
  <appender name="CONSOLE"
    class="ch.qos.logback.core.ConsoleAppender">
    <!-- deny all events with a level below INFO, that is TRACE and DEBUG -->
    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
      <level>INFO</level>
    </filter>
    <encoder>
      <pattern>
        %-4relative [%thread] %-5level %logger{30} - %msg%n
      </pattern>
    </encoder>
  </appender>
  <root level="DEBUG">
    <appender-ref ref="CONSOLE" />
  </root>
</configuration>



```

##  **详细的logback-spring.xml示例** 

 以上介绍了xml中重要的几个元素，下面将我配置的xml贴出来以供参考（实现了基于日期和大小翻滚的策略，以及经INFO和ERROR日志区分输出，还有规范日志输出格式等）： 

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
 
    <!-- appender是configuration的子节点，是负责写日志的组件。 -->
    <!-- ConsoleAppender：把日志输出到控制台 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 默认情况下，每个日志事件都会立即刷新到基础输出流。 这种默认方法更安全，因为如果应用程序在没有正确关闭appender的情况下退出，则日志事件不会丢失。
         但是，为了显着增加日志记录吞吐量，您可能希望将immediateFlush属性设置为false -->
        <!--<immediateFlush>true</immediateFlush>-->
        <encoder>
            <!-- %37():如果字符没有37个字符长度,则左侧用空格补齐 -->
            <!-- %-37():如果字符没有37个字符长度,则右侧用空格补齐 -->
            <!-- %15.15():如果记录的线程字符长度小于15(第一个)则用空格在左侧补齐,如果字符长度大于15(第二个),则从开头开始截断多余的字符 -->
            <!-- %-40.40():如果记录的logger字符长度小于40(第一个)则用空格在右侧补齐,如果字符长度大于40(第二个),则从开头开始截断多余的字符 -->
            <!-- %msg：日志打印详情 -->
            <!-- %n:换行符 -->
            <!-- %highlight():转换说明符以粗体红色显示其级别为ERROR的事件，红色为WARN，BLUE为INFO，以及其他级别的默认颜色。 -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) --- [%15.15(%thread)] %cyan(%-40.40(%logger{40})) : %msg%n</pattern>
            <!-- 控制台也要使用UTF-8，不要使用GBK，否则会中文乱码 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>
 
    <!-- info 日志-->
    <!-- RollingFileAppender：滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件 -->
    <!-- 以下的大概意思是：1.先按日期存日志，日期变了，将前一天的日志文件名重命名为XXX%日期%索引，新的日志仍然是project_info.log -->
    <!--             2.如果日期没有发生变化，但是当前日志的文件大小超过10MB时，对当前日志进行分割 重命名-->
    <appender name="info_log" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--日志文件路径和名称-->
        <File>logs/project_info.log</File>
        <!--是否追加到文件末尾,默认为true-->
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>DENY</onMatch><!-- 如果命中ERROR就禁止这条日志 -->
            <onMismatch>ACCEPT</onMismatch><!-- 如果没有命中就使用这条规则 -->
        </filter>
        <!--有两个与RollingFileAppender交互的重要子组件。 第一个RollingFileAppender子组件，即RollingPolicy:负责执行翻转所需的操作。
         RollingFileAppender的第二个子组件，即TriggeringPolicy:将确定是否以及何时发生翻转。 因此，RollingPolicy负责什么和TriggeringPolicy负责什么时候.
        作为任何用途，RollingFileAppender必须同时设置RollingPolicy和TriggeringPolicy,但是，如果其RollingPolicy也实现了TriggeringPolicy接口，则只需要显式指定前者。-->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 日志文件的名字会根据fileNamePattern的值，每隔一段时间改变一次 -->
            <!-- 文件名：logs/project_info.2017-12-05.0.log -->
            <!-- 注意：SizeAndTimeBasedRollingPolicy中 ％i和％d令牌都是强制性的，必须存在，要不会报错 -->
            <fileNamePattern>logs/project_info.%d.%i.log</fileNamePattern>
            <!-- 每产生一个日志文件，该日志文件的保存期限为30天, ps:maxHistory的单位是根据fileNamePattern中的翻转策略自动推算出来的,例如上面选用了yyyy-MM-dd,则单位为天
            如果上面选用了yyyy-MM,则单位为月,另外上面的单位默认为yyyy-MM-dd-->
            <maxHistory>30</maxHistory>
            <!-- 每个日志文件到10mb的时候开始切分，最多保留30天，但最大到20GB，哪怕没到30天也要删除多余的日志 -->
            <totalSizeCap>20GB</totalSizeCap>
            <!-- maxFileSize:这是活动文件的大小，默认值是10MB，测试时可改成5KB看效果 -->
            <maxFileSize>10MB</maxFileSize>
        </rollingPolicy>
        <!--编码器-->
        <encoder>
            <!-- pattern节点，用来设置日志的输入格式 ps:日志文件中没有设置颜色,否则颜色部分会有ESC[0:39em等乱码-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%15.15(%thread)] %-40.40(%logger{40}) : %msg%n</pattern>
            <!-- 记录日志的编码:此处设置字符集 - -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>
 
    <!-- error 日志-->
    <!-- RollingFileAppender：滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件 -->
    <!-- 以下的大概意思是：1.先按日期存日志，日期变了，将前一天的日志文件名重命名为XXX%日期%索引，新的日志仍然是project_error.log -->
    <!--             2.如果日期没有发生变化，但是当前日志的文件大小超过10MB时，对当前日志进行分割 重命名-->
    <appender name="error_log" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--日志文件路径和名称-->
        <File>logs/project_error.log</File>
        <!--是否追加到文件末尾,默认为true-->
        <append>true</append>
        <!-- ThresholdFilter过滤低于指定阈值的事件。 对于等于或高于阈值的事件，ThresholdFilter将在调用其decision（）方法时响应NEUTRAL。 但是，将拒绝级别低于阈值的事件 -->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level><!-- 低于ERROR级别的日志（debug,info）将被拒绝，等于或者高于ERROR的级别将相应NEUTRAL -->
        </filter>
        <!--有两个与RollingFileAppender交互的重要子组件。 第一个RollingFileAppender子组件，即RollingPolicy:负责执行翻转所需的操作。
        RollingFileAppender的第二个子组件，即TriggeringPolicy:将确定是否以及何时发生翻转。 因此，RollingPolicy负责什么和TriggeringPolicy负责什么时候.
       作为任何用途，RollingFileAppender必须同时设置RollingPolicy和TriggeringPolicy,但是，如果其RollingPolicy也实现了TriggeringPolicy接口，则只需要显式指定前者。-->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 活动文件的名字会根据fileNamePattern的值，每隔一段时间改变一次 -->
            <!-- 文件名：logs/project_error.2017-12-05.0.log -->
            <!-- 注意：SizeAndTimeBasedRollingPolicy中 ％i和％d令牌都是强制性的，必须存在，要不会报错 -->
            <fileNamePattern>logs/project_error.%d.%i.log</fileNamePattern>
            <!-- 每产生一个日志文件，该日志文件的保存期限为30天, ps:maxHistory的单位是根据fileNamePattern中的翻转策略自动推算出来的,例如上面选用了yyyy-MM-dd,则单位为天
            如果上面选用了yyyy-MM,则单位为月,另外上面的单位默认为yyyy-MM-dd-->
            <maxHistory>30</maxHistory>
            <!-- 每个日志文件到10mb的时候开始切分，最多保留30天，但最大到20GB，哪怕没到30天也要删除多余的日志 -->
            <totalSizeCap>20GB</totalSizeCap>
            <!-- maxFileSize:这是活动文件的大小，默认值是10MB，测试时可改成5KB看效果 -->
            <maxFileSize>10MB</maxFileSize>
        </rollingPolicy>
        <!--编码器-->
        <encoder>
            <!-- pattern节点，用来设置日志的输入格式 ps:日志文件中没有设置颜色,否则颜色部分会有ESC[0:39em等乱码-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%15.15(%thread)] %-40.40(%logger{40}) : %msg%n</pattern>
            <!-- 记录日志的编码:此处设置字符集 - -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>
 
    <!--给定记录器的每个启用的日志记录请求都将转发到该记录器中的所有appender以及层次结构中较高的appender（不用在意level值）。
    换句话说，appender是从记录器层次结构中附加地继承的。
    例如，如果将控制台appender添加到根记录器，则所有启用的日志记录请求将至少在控制台上打印。
    如果另外将文件追加器添加到记录器（例如L），则对L和L'子项启用的记录请求将打印在文件和控制台上。
    通过将记录器的additivity标志设置为false，可以覆盖此默认行为，以便不再添加appender累积-->
    <!-- configuration中最多允许一个root，别的logger如果没有设置级别则从父级别root继承 -->
    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
 
    <!-- 指定项目中某个包，当有日志操作行为时的日志记录级别 -->
    <!-- 级别依次为【从高到低】：FATAL > ERROR > WARN > INFO > DEBUG > TRACE  -->
    <logger name="com.sailing.springbootmybatis" level="INFO">
        <appender-ref ref="info_log" />
        <appender-ref ref="error_log" />
    </logger>
 
    <!-- 利用logback输入mybatis的sql日志，
    注意：如果不加 additivity="false" 则此logger会将输出转发到自身以及祖先的logger中，就会出现日志文件中sql重复打印-->
    <logger name="com.sailing.springbootmybatis.mapper" level="DEBUG" additivity="false">
        <appender-ref ref="info_log" />
        <appender-ref ref="error_log" />
    </logger>
 
    <!-- additivity=false代表禁止默认累计的行为，即com.atomikos中的日志只会记录到日志文件中，不会输出层次级别更高的任何appender-->
    <logger name="com.atomikos" level="INFO" additivity="false">
        <appender-ref ref="info_log" />
        <appender-ref ref="error_log" />
    </logger>
 
</configuration>
```



## 附加内容

5.1：这里再说下log日志输出代码，一般有人可能在代码中使用如下方式输出：

```
Object entry = new SomeObject(); 
logger.debug("The entry is " + entry);
```

5.2：上面看起来没什么问题，但是会存在构造消息参数的成本，即将entry转换成字符串相加。并且无论是否记录消息，都是如此，即：那怕日志级别为INFO，也会执行括号里面的操作，但是日志不会输出，下面是优化后的写法：

```
if(logger.isDebugEnabled()) { 
    Object entry = new SomeObject(); 
    logger.debug("The entry is " + entry);
}
```

5.3：5.2的写法，首先对设置的日志级别进行了判断，如果为debug模式，才进行参数的构造，对第一种写法进行了改善。不过还有最好的写法，使用占位符：

```
Object entry = new SomeObject(); 
logger.debug("The entry is {}.", entry);
```


只有在评估是否记录之后，并且只有在决策是肯定的情况下，记录器实现才会格式化消息并将“{}”对替换为条目的字符串值。 换句话说，当禁用日志语句时，此表单不会产生参数构造的成本。

logback作者进行测试得出：第一种和第三种写法将产生完全相同的输出。 但是，在禁用日志记录语句的情况下，第三个变体将比第一个变体优于至少30倍。

如果有多个参数，写法如下：

```
logger.debug("The new entry is {}. It replaces {}.", entry, oldEntry);
```


如果需要传递三个或更多参数，则还可以使用Object []变体：

```
Object[] paramArray = {newVal, below, above};
logger.debug("Value {} was inserted between {} and {}.", paramArray);
```

5.4：记录日志的时候我们可能需要在文件中记录下异常的堆栈信息，经过测试，logger.error(e) 不会打印出堆栈信息，正确的写法是：

```
logger.error("程序异常, 详细信息:{}", e.getLocalizedMessage() , e);
```




## 创建logback-spring.xml

首先，官方推荐使用的xml名字的格式为：logback-spring.xml而不是logback.xml，至于为什么，因为带spring后缀的可以使用`<springProfile>`这个标签。

在resource下创建logback-spring.xml文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration  scan="true" scanPeriod="10 seconds">

    <!--<include resource="org/springframework/boot/logging/logback/base.xml" />-->

    <contextName>logback</contextName>



    <!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义变量后，可以使“${}”来使用变量。 -->
    <!--<property name="log.path" value="D:/deleteme/logback" />-->


    <!-- 存放日志的目录 -->
    <!-- 从application.yml中读取logging.path配置,如果没有配置,则默认使用defaultValue -->
    <springProperty scope="context" name="log.path" source="logging.path" defaultValue="localhost.log"/>



    <!-- 彩色日志 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>





    <!-- 下面内容 每一个appender是配置了一种日志输出的组件 -->


    <!--定义了一个名为CONSOLE的logger , 目标：将日志输出到控制台, 输出的日志级别：INFO及以上-->
    <!-- ch.qos.logback.core.ConsoleAppender 是将日志输出到控制台 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 定义一个输出到控制台的日志组件，目标：将日志输出到控制台，, 输出的日志级别：INFO及以上 -->
    <appender name="CONSOLE_COLOR" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>info</level>
        </filter>
        <encoder>
            <!-- %37():如果字符没有37个字符长度,则左侧用空格补齐 -->
            <!-- %-37():如果字符没有37个字符长度,则右侧用空格补齐 -->
            <!-- %15.15():如果记录的线程字符长度小于15(第一个)则用空格在左侧补齐,如果字符长度大于15(第二个),则从开头开始截断多余的字符 -->
            <!-- %-40.40():如果记录的logger字符长度小于40(第一个)则用空格在右侧补齐,如果字符长度大于40(第二个),则从开头开始截断多余的字符 -->
            <!-- %msg：日志打印详情 -->
            <!-- %n:换行符 -->
            <!-- %highlight():转换说明符以粗体红色显示其级别为ERROR的事件，红色为WARN，BLUE为INFO，以及其他级别的默认颜色。 -->
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) --- [%15.15(%thread)] %cyan(%-40.40(%logger{40})) : %msg%n
            </pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>


    <!-- 定义一个输出到文件的日志组件, 目标：将日志输出到文件, 输出的日志级别：仅仅DEBUG -->
    <!-- 时间滚动输出 level为 DEBUG 日志 -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_debug.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
            </pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${log.path}/debug/log-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>



        <!-- 此日志文件只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!--
            onMismatch（匹配不成功） 有2个值： ACCEPT 和 DENY
            输出的级别等于 level 标签里的 级别时：
            如果值 = ACCEPT ，则输出
            如果值 = DENY , 不输出
            -->
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>




    <!-- 定义一个输出到文件的日志组件, 目标：将日志输出到文件, 输出的日志级别：仅仅INFO级别 -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_info.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <!-- %37():如果字符没有37个字符长度,则左侧用空格补齐 -->
            <!-- %-37():如果字符没有37个字符长度,则右侧用空格补齐 -->
            <!-- %15.15():如果记录的线程字符长度小于15(第一个)则用空格在左侧补齐,如果字符长度大于15(第二个),则从开头开始截断多余的字符 -->
            <!-- %-40.40():如果记录的logger字符长度小于40(第一个)则用空格在右侧补齐,如果字符长度大于40(第二个),则从开头开始截断多余的字符 -->
            <!-- %msg：日志打印详情 -->
            <!-- %n:换行符 -->
            <!-- %highlight():转换说明符以粗体红色显示其级别为ERROR的事件，红色为WARN，BLUE为INFO，以及其他级别的默认颜色。 -->
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
            </pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/info/log-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 定义一个输出到文件的日志组件, 目标：将日志输出到文件, 输出的日志级别：仅仅WARN级别 -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_warn.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/warn/log-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>


    <!-- 定义一个输出到文件的日志组件, 目标：将日志输出到文件, 输出的日志级别：仅仅ERROR级别 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_error.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/error/log-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文件保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文件只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--
        <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
        以及指定<appender>。<logger>仅有一个name属性，
        一个可选的level和一个可选的addtivity属性。
        name:用来指定受此logger约束的某一个包或者具体的某一个类。
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
              还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
              如果未设置此属性，那么当前logger将会继承上级的级别。
        addtivity:是否向上级logger传递打印信息。默认是true。
    -->
    <!--<logger name="org.springframework.web" level="info"/>-->
    <!--<logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>-->
    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
     -->


    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->






    <!-- 下面内容需要在applicatoin中设置spring.profile使用哪个配置 dev:开发环境 test:测试环境 prod:生产环境 -->
    <!--开发环境:打印ERROR和INFO级的日志-->
    <springProfile name="dev">
        <logger name="com.thd.springboottest" level="info">
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>
    <!--测试环境:打印ERROR和INFO级的日志-->
    <springProfile name="dev">
        <logger name="com.thd.springboottest" level="info">
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>
    <!--生产环境:只打印ERROR级日志-->
    <springProfile name="prod">
        <logger name="com.thd.springboottest" level="error">
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>

    <root level="INFO">

        <appender-ref ref="CONSOLE_COLOR" />
        <!-- <appender-ref ref="CONSOLE" />
        <appender-ref ref="DEBUG_FILE" />
        <appender-ref ref="INFO_FILE" />
        <appender-ref ref="WARN_FILE" />
        <appender-ref ref="ERROR_FILE" />-->
    </root>

<!--    <logger name="com.thd.springboottest" level="INFO">-->
<!--        <appender-ref ref="INFO_FILE" />-->
<!--        <appender-ref ref="ERROR_FILE" />-->
<!--    </logger>-->





    <!--生产环境:输出到文件-->
    <!--<springProfile name="pro">-->
    <!--<root level="info">-->
    <!--<appender-ref ref="CONSOLE" />-->
    <!--<appender-ref ref="DEBUG_FILE" />-->
    <!--<appender-ref ref="INFO_FILE" />-->
    <!--<appender-ref ref="ERROR_FILE" />-->
    <!--<appender-ref ref="WARN_FILE" />-->
    <!--</root>-->
    <!--</springProfile>-->

</configuration>
```

特别注意一下这个配置

```
<!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义变量后，可以使“${}”来使用变量。 -->
<!--<property name="log.path" value="D:/deleteme/logback" />-->

<!-- 从application.yml中读取logging.path配置,如果没有配置,则默认使用defaultValue -->
<springProperty scope="context" name="log.path" source="logging.path" defaultValue="localhost.log"/>
```

property是定义固定的属性，也就是说`<property name="log.path" value="D:/deleteme/logback" />`这个定义的log.path可以在后面的配置中通过${log.path}来代表D:/deleteme/logback路径

而`<springProperty scope="context" name="log.path" source="logging.path" defaultValue="localhost.log"/>`是从application.yml中获取logging.path这个配置的值，如果没有则使用defaultValue中的值，同样可以通过${log.path}来获取application.yml中获取logging.path的配置内容



注：上面配置可以配合application.yml中的spring.profile来指定环境

```
spring:
  profiles:
    active: prod
```





## 使用过程



1. yml中配置logback

```
logging:
  config: classpath:config/logback-spring.xml
  path: D:/deleteme/logback

```

2. 添加logback-spring.xml文件
    logging.config配置的是lockback-spring.xml的位置，本例中的位置为resources/config/logback-spring.xml

  详细内容参见`创建logback-spring.xml`一节

3. 如果需要，指定运行环境

```
spring:
  profiles:
    active: prod
```

改配置需要结合logback-spring.xml中的配置使用

```
<!-- 下面内容需要在applicatoin中设置spring.profile使用哪个配置 dev:开发环境 test:测试环境 prod:生产环境 -->
    <!--开发环境:打印ERROR和INFO级的日志-->
    <springProfile name="dev">
        <logger name="com.thd.springboottest" level="info">
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>
    <!--测试环境:打印ERROR和INFO级的日志-->
    <springProfile name="dev">
        <logger name="com.thd.springboottest" level="info">
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>
    <!--生产环境:只打印ERROR级日志-->
    <springProfile name="prod">
        <logger name="com.thd.springboottest" level="error">
            <appender-ref ref="ERROR_FILE" />
        </logger>
    </springProfile>
```



日志的使用方式，就跟普通使用一样，只不过，此配置配置的是不仅在控制台输出而且在磁盘上也会保留，并且info/debug/error/warn的日志都区分开了，并且按照每天做了归档 



## 总结

logback-spring.xml配置思路

1. 配置一个一个appender，根据不同的**日志级别**和**输出目的地**定义所有需要的日志输出工具。例如：info级别的输出到控制台,error级别的输出到文件也输出到控制台
2. 定义一个全局日志，配置其使用的日志工具（与上面说的appender是1对多的关系，调用log.info()/log.debug()/log.error()...时候可以根据不同的级别使用不同的appender组合）
3. 如果需要，可以定义不同包下的类使用的日志工具不同。
4. 可以根据代码运行环境不同，制定多套日志输出规则



