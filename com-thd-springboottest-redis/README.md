[TOC]

# Jedis

> jedis jedispool Redistemplate

在大型系统数据读请求中，基本上90%都可以通过分布式缓存集群来抗下来，而 Redis 又是分布式缓存集群的主要践行者，因此了解 Redis 是必不可少的技能。

在 javaWeb 中实现对 Redis 的操作，主要有两种方式：Jedis、RedisTemplate。

# 什么是 Jedis？什么是 RedisTemplate？

Jedis 是 Redis 官方推荐的面向 Java 的操作Redis 的客户端，通过jedis我们可以实现连接Redis，以及操作 Redis 。

RedisTemplate 是 SpringDataRedis 中对JedisAp i的高度封装。SpringDataRedis 相对于 Jedis 来说可以方便地更换 Redis 的 Java客户端，比 Jedis 多了自动管理连接池的特性，方便与其他 Spring 框架进行搭配使用如：SpringCache

# Jedis 的使用

就像在学习 springmvc 框架之前学习 servlet 一样，了解 jedis 的使用，看一下单机中 Jedis 的使用，首先要导入相关架包，
[jedis.jar](https://mvnrepository.com/artifact/redis.clients/jedis)

```
import redis.clients.jedis.Jedis;public class RedisJava {    public static void main(String[] args) {        //连接本地的 Redis 服务        Jedis jedis = new Jedis("localhost");        System.out.println("连接成功");        //查看服务是否运行        System.out.println("服务正在运行: "+jedis.ping());    }}
```

编译以上 Java 程序，确保驱动包的路径是正确的，打印如下：

```
连接成功服务正在运行: PONG
```

**redis 中最主要的就是读写数据。Redis 操作5大基本类型：String、List、Hash、Set、SortedSe。t**
菜鸟教程中关于 Redis 操作 String 、List 等：http://www.runoob.com/redis/redis-java.html

# 连接池 JedisPool，为什么要用 JedisPool

首先我们如果每次使用缓存都生成一个 Jedis 对象的话，这样意味着会建立很多 socket 连接，造成系统资源被不可控调用，甚至会导致奇怪错误的发生。

如果使用单例模式，在线程安全模式下适应不了高并发的需求，非线程安全模式又可能会出现与时间相关的错误。

因此，为了避免这些问题，引入了池的概念 JedisPool。JedissPool 是一个线程安全的网络连接池，我们可以通过 JedisPool 创建和管理 Jedis 实例，这样可以有效的解决以上问题以实现系统的高性能。

我们可以理解成项目中的数据库连接池，例如：阿里巴巴的 druid~

# 直连和使用连接池的对比

|        | 优点                                                      | 缺点                                                         |
| :----- | :-------------------------------------------------------- | :----------------------------------------------------------- |
| 直连   | 简单方便适用于少量长期连接的场景                          | 存在每次新建/关闭TCP开销，资源无法控制，存在连接泄露的可能，Jedis对象线程不安全 |
| 连接池 | Jedis预先生成，降低开销，连接池的形式保护和控制资源的使用 | 相对于直连，使用相对麻烦，尤其在资源管理上需要很多参数来保证，一旦规划不合理也会出现问题。 |

# 如何创建 JedisPool 实例和 Jedis 实例对象

![](01.png)

```
private static JedisPool pool = null;

if( pool == null ){
    JedisPoolConfig config = new JedisPoolConfig();
    控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
    如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    config.setMaxTotal(50); 
    控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
    config.setMaxIdle(5);
    表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
    小于零:阻塞不确定的时间,  默认-1
    config.setMaxWaitMillis(1000*100);
    在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    config.setTestOnBorrow(true);
    return 一个jedis实例给pool时，是否检查连接可用性（ping()）
    config.setTestOnReturn(true);
    connectionTimeout 连接超时（默认2000ms）
    soTimeout 响应超时（默认2000ms）
}

获取实例
public static Jedis getJedis() {
    return pool.getResource();
}

释放 redis
public static void returnResource(Jedis jedis) {
    if(jedis != null) {
        jedis.close();
    }
}
```

# JedisPool 属性配置（JedisPoolConfig）

 JedisPool的配置参数大部分是由JedisPoolConfig的对应项来赋值的。 

![](02.png)

# 再回头看 RedisTemplate

## 6.1、关于 RedisTemplate

通过最开始的简单概述，我们了解到 SpringDataRedis（RedisTemplate） 相对于 Jedis 来说可以方便地更换 Redis 的 Java 客户端，比 Jedis 多了自动管理连接池的特性，方便与其他 Spring 框架进行搭配使用如：SpringCache；

或者可以理解成，redisTemplate 是对 Jedis 的对 redis 操作的扩展，有更多的操作， 封装使操作更便捷。

## 6.2、如何使用 RedisTemplate

首先说一下，序列化，因为 redis 存储的数据必须要经过序列化处理。

那么你要知道 SDK 默认采用的序列化策略有两种，一种是 String 的序列化策略，一种是 JDK 的序列化策略。

在说如何使用之前再引申出来个 StringRedisTemplate….

## 6.3、StringRedisTemplate 和 RedisTemplate 区别

通过上边我们清楚了 RedisTemplate ，但是这个 StringRedisTemplate 又是个啥呢？

其实作用是一样的？ what？？？

其实他们两者之间的区别主要在于他们使用的序列化类。

> RedisTemplate 使用的是 JdkSerializationRedisSerializer （JDK 序列化）
> StringRedisTemplate 使用的是 StringRedisSerializer（String 序列化）

## 6.4、Redis 序列化：（String 序列化，JDK 序列化）

**RedisTemplate ：**

RedisTemplate 使用的序列类在在操作数据的时候，比如说存入数据会将数据先序列化成字节数组，然后在存入 Redis 数据库，这个时候打开 Redis 查看的时候，你会看到你的数据不是以可读的形式展现的，而是以字节数组显示，类似下面：

![img](03.png)

当然从Redis获取数据的时候也会默认将数据当做字节数组转化，这样就会导致一个问题，当需要获取的数据不是以字节数组存在 redis 当中而是正常的可读的字符串的时候，比如说下面这种形式的数据:

![img](04.png)

RedisTemplate就无法获取导数据，这个时候获取到的值就是NULL。这个时候StringRedisTempate就派上了用场。 

 

**StringRedisTemplate**
当Redis当中的数据值是以可读的形式显示出来的时候，只能使用StringRedisTemplate才能获取到里面的数据。
所以当你使用RedisTemplate获取不到数据的时候请检查一下是不是Redis里面的数据是可读形式而非字节数组。

**序列化总结**
当你的 redis 数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用 StringRedisTemplate 即可，但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从 Redis 里面取出一个对象，那么使用RedisTemplate 是更好的选择。

# 总结

Jedis 是 Redis 官方推荐的面向 Java 操作 Redis 的客户端，但在项目中使用 **Jedis jedis = new Jedis("xxx");** 的操作有失大雅，就好比还在用 servlet…，同时用 JedisPool 来获得连接进行 get、set、del 等操作也相对简单，但是需要注意的是，**存入 Redis 是需要序列化的**，至于选择哪种序列化需要自己抉择;

再说到 RedisTemplate，Spring 针对 Redis 的使用，封装了一个比较强大的 Template ，在没有这个 Template 之前，是使用 Jedis 直连进行相应的交互操作，值得一提的是，做这个封装的是 SpringData，简单了解一下 SpringData：

> Spring Data: Spring 的一个子项目。用于简化数据库访问，支持NoSQL和关系数据库存储。其主要目标是使数据库的访问变得方便快捷。

Spring Data 项目所支持 NoSQL 存储：

```
  - - MongoDB（文档数据库）  
  - - Neo4j （图形数据库） 
  - - Redis（键/值存储）  
  - - Hbase（列族数据库）
```

Spring Data 项目所支持的关系数据存储技术：

```
   - - JDBC   - - JPA
```

从之前了解的 SpringDataJpa，我们不难想象，RedisTemplate 的使用也一定是非常简单的，下一篇看一下项目中的应用。

最后：来都来了，左上角不关注一下吗。