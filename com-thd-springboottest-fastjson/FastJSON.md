# FastJSON 

# 一些序列化注释

```
	/**
     * @JSONField 注释属性含义：
     *
     * public @interface JSONField {
     *     // 配置序列化和反序列化的顺序，1.1.42版本之后才支持
     *     int ordinal() default 0;
     *
     *      // 指定字段的名称
     *     String name() default "";
     *
     *     // 指定字段的格式，对日期格式有用
     *     String format() default "";
     *
     *     // 是否序列化
     *     boolean serialize() default true;
     *
     *     // 是否反序列化
     *     boolean deserialize() default true;
     * }
     */
```

例如

```
@JSONField(ordinal=1,name="userId")
private String id;
@JSONField(ordinal=2)
private String userName;
@JSONField(serialize=false)
private String password;
@JSONField(ordinal=3)
private String phone;
@JSONField(ordinal=4,format="yyyy-MM-dd")
private Date birthday;
@JSONField(ordinal=5,deserialize=false)
private int age;
```



# JSON类常用API

## 序列化方法

### 将对象序列化成json字符串 

JSON.toJSONString

> public static String toJSONString(Object object);

例：

```
User u = new User(1,"devil13th");
String json = JSON.toJSONString(u);
// 序列化结果
{"birthday":null,"phone":"","roles":[],"userName":"devil13th","userId":"1","age":0}
```



### 带有选项的序列化

JSON.toJSONString

> public static String toJSONString(Object object, SerializerFeature... features);

SerializerFeature 参数说明：

```
* SerializerFeature.WriteClassName          |   将全类名放到@type属性中
* SerializerFeature.WriteNullListAsEmpty    |   将Collection类型字段的字段空值输出为[]
* SerializerFeature.WriteNullStringAsEmpty  |	将字符串类型字段的空值输出为空字符串 ""
* SerializerFeature.WriteNullNumberAsZero   |	将数值类型字段的空值输出为0
* SerializerFeature.WriteNullBooleanAsFalse |	将Boolean类型字段的空值输出为false
```

例子：

```
User u = new User();
u.setId("1");
u.setUserName("devil13th");
String json = JSON.toJSONString(u,SerializerFeature.WriteClassName);
//结果 - 带有@type属性,设置了序列化的全类名
{"@type":"com.thd.springboottest.fastjson.entity.User","userId":"1","userName":"devil13th","age":0}
```



## 反序列化方法

### 反序列化成JSONObject

JSON.parse

> public static JSONObject parseObject(String text)

例：
```
Object obj = JSON.parseObject(json);
```

### 指定反序列化的目标类 - 非集合泛型类

JSON.parse

> public static <T> T parseObject(String text, Class<T> clazz)

例：

```
User u2 = (User)JSON.parseObject(json,User.class);
```

### 指定反序列化的目标类 - 集合泛型类

JSON.parse

> public static <T> T parseObject(String text, TypeReference<T> type, Feature... features) 

例：

```
User u = new User();
u.setId("1");
u.setUserName("devil13th");
String json = JSON.toJSONString(u);
System.out.println(json);
User jsonObj = (User)JSON.parseObject(json,new TypeReference<User>(){});
return ResponseEntity.ok(jsonObj);
```



## 反序列化成JSONObject

> public static JSONObject parseObject(String text)

# 总结

如果使用fastjson反序列化成某个指定类型的两种方式

1. 要么在序列化的时候设置参数指定json字符串反序列化的类型 `JSON.toJSONString(u,SerializerFeature.WriteClassName);`
2. 要么在反序列化的时候指定目标类的类型

`(User)JSON.parseObject(json,User.class);` 或使用TypeReference指定目标类类型 `(User)JSON.parseObject(json,new TypeReference<User>(){})`



序列化的时候指定类型使用`JSON.toJSONString(u,SerializerFeature.WriteClassName);`

反序列化的时候指定类型使用`JSON.parseObject(json,User.class);`

只有在序列化的时候或反序列化的时候指定了对象类型，才可以将反序列化结果强转成某个指定类型的对象

如果序列化和反序列化都没有指定类型，则反序列化会返回JSONObject对象

如果在序列化或反序列化的时候指定了对象类型则反序列化不能返回JSONObject对象



