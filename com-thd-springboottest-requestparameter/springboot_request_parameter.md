[TOC]

# SpringBoot中获取Request中的数据

> SpringBoot Request 获取请求数据 Converter Json Jackson Fastjson 请求数据中的序列化与反序列化 serializer deserializer  request method post put get delete 请求方法 请求中的数据转换  date timestamp HttpMessageConverter @PathVariable @ModelAttribute @RequestParam @RequestBody

## SpringBoot 中获取请求数据的几种方式

根据请求中携带数据的位置不同，SpringBoot常用的获取请求中数据的方式有以下几种：

```
数据的位置
	|- URL中(put,get,post,delete)
		|- URL中?后面的数据 例如 http://127.0.0.1/x/y?a=1&b=2&c=3
		|- RestFul形式的路径数据  例如http://127.0.0.1/x/1/2/3
	|- 请求体中(put,post)
		|- JSON格式的数据 content-type为application/json 
		|- 类似于x=y&a=b的数据 content-type为application/x-www-form-urlencoded
		|- formDate数据  content-type为multipart/form-data
```

**注意：数据如果放在请求体中，则必须使用put或post类型的请求，因为只有这两种类型才会有请求体，Get请求是不会有请求体的**

下面分别举例

### 获取URL中？后面的数据(Get/Delete请求)

例子1：将URL中?后面的数据转换成变量

```
@ResponseBody
@RequestMapping(value="/testGet01",method=RequestMethod.GET)
//直接获取url?后面对应的参数
//url : http://127.0.0.1:8899/thd/requestparameter/testGet01?usr=devil13th&pwd=123456&birthday=2015_10_10 10:11:12&createTime=1586085091
public String testGet01(@RequestParam String usr,
                        @RequestParam String pwd,
                        @RequestParam Date birthday,
                        @RequestParam Timestamp createTime){
    this.log.info("testGet01");
    System.out.println(usr + "," + pwd + "," + birthday + "," + createTime );
    return (usr + "," + pwd);
}
```

使用 RestTemplate进行请求：

```
String url =  CommonURL.URL +"/testGet01?usr=devil13th&pwd=123456&birthday=2015_10_10 10:11:12&createTime=1586085091";
RestTemplate rt = new RestTemplate();
ResponseEntity<String> responseEntity = rt.getForEntity(url,String.class);
System.out.println(" ========== 接收信息 ===========");
System.out.println("response body:" + responseEntity.getBody());
System.out.println("response header:" + responseEntity.getHeaders());
```



例子2：将URL中?后面的数据转换成对象的属性

```
@ResponseBody
@RequestMapping(value="/testGet01a",method=RequestMethod.GET)
//直接获取url?后面对应的参数 用对象来接收
//url : http://127.0.0.1:8899/thd/requestparameter/testGet01a?name=devil13th&age=15&birthday=2015_10_10 10:11:12&createTime=1586085091
public ResponseEntity testGet01a(Person person){
    this.log.info("testGet01a");
    System.out.println(person);
    return ResponseEntity.ok(person);
}
```



### 获取RestFul形式的路径数据(Get/Delete请求)

```
@ResponseBody
@RequestMapping(value="/testGet02/{date}/{timestamp}",method=RequestMethod.GET)
// 通过在url中使用{}占位符来获取参数
// restful风格的路径参数
//url : http://127.0.0.1:8899/thd/requestparameter/testGet02/2015_01_01/1586085091
public ResponseEntity testGet02(@PathVariable Date date,@PathVariable Timestamp timestamp ){
    this.log.info("testGet02");
    System.out.println(timestamp);
    System.out.println(date);
    Person p = new Person();
    p.setBirthday(date);
    p.setCreateTime(timestamp);
    return ResponseEntity.ok(p);
}
```



使用 RestTemplate进行请求：

```
String url =  CommonURL.URL +"/testGet02/2015_01_01/1586085091";
RestTemplate rt = new RestTemplate();
ResponseEntity<String> responseEntity = rt.getForEntity(url,String.class);
System.out.println(" ========== 接收信息 ===========");
System.out.println("response body:" + responseEntity.getBody());
System.out.println("response header:" + responseEntity.getHeaders());
```



### 获取请求体中JSON格式的数据(Put/Post请求)

这种用法要有2个前提

1. 这种获取数据的方式，请求是PUT或POST的方式（只有这两种方式才会有请求体）
2. 而且请求头中的`content-type`属性是`application/json`

请求信息：

```
POST /thd/requestparameter/testPost01 HTTP/1.1
Host: 127.0.0.1:8899
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: c8efa431-a56e-8d99-6084-ecf1760daa5c

{
	"name":"thd",
	"age":5,
	"birthday":"2013_01_01 23:11:12",
	"createTime":"2012&&12&&21 10$$23$$22"
}
```

获取数据：

```
@ResponseBody
@RequestMapping(value="/testPost01",method=RequestMethod.POST)
//通过Post方式来提交body内容(一般是json),可以通过@RequestBody直接将body中的json转成对象
//注意这种方式要设置头部信息的contentType属性 headers.set('Content-Type', 'application/json');
//url : http://127.0.0.1:8899/thd/requestparameter/testPost01
public Person testPost01(@RequestBody Person person){
    this.log.info("testPost01");
    System.out.println(person);
    return person;
}
```

RestTemplate进行请求：

```
String url = CommonURL.URL +"/testPost01";
      
RestTemplate rt = new RestTemplate();

// 请求体内容
JSONObject obj = new JSONObject();
obj.put("age","5");
obj.put("name","devil13th");
obj.put("birthday","2013_01_02 23:11:24");
obj.put("createTime","2013&&03&&23 23$$11$$24");
// 设置请求头内容
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // 设置content-type
headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE); // 设置Accept

HttpEntity<String> requestEntity = new HttpEntity<String>(obj.toJSONString(),headers);

System.out.println(" ========== 发送信息 ===========");
System.out.println("headers:" + requestEntity.getHeaders());
System.out.println("body:" + requestEntity.getBody());

ResponseEntity<String> responseEntity = rt.postForEntity(url,requestEntity,String.class);

System.out.println(" ========== 接收信息 ===========");
System.out.println("response body:" + responseEntity.getBody());
System.out.println("response header:" + responseEntity.getHeaders());
```



### 获取请求体中类似于x=y&a=b的数据(Put/Post请求)

注：这种获取数据的方式，请求是PUT或POST的方式（只有这两种方式才会有请求体）

请求的信息：

```
POST /thd/requestparameter/testPost02 HTTP/1.1
Host: 127.0.0.1:8899
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache
Postman-Token: 36d3e7ac-8e97-79e5-9608-76e6ca8c869e

name=thd&age=4&birthday=2015_10_10+10%3A11%3A12&createTime=1586085091
```

处理数据

```
@ResponseBody
@RequestMapping(value="/testPost02",method=RequestMethod.POST)
// 客户端以formData的方式发送
// form 表单提交接收参数需要使用@ModelAttribute("person"),
// 表单中的name不要加person前缀，例如name="person.id"应写成name="id"
//url : http://127.0.0.1:8899/thd/requestparameter/testPost02
public Person testPost02(@ModelAttribute("person") Person person){
    this.log.info("testPost02");
    System.out.println(person);
    return person;
}
```

使用RestTemplate进行请求

```
String url = CommonURL.URL +"/testPost02";

RestTemplate rt = new RestTemplate();


// 请求体内容
MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
map.add("age","6");
map.add("name","devil13th");
map.add("birthday","2015_10_10 10:11:12");
map.add("createTime","1586085091");
// 设置请求头内容
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 设置content-type 为 application/x-www-form-urlencoded
headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE); // 设置Accept


HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map,headers);
System.out.println(" ========== 发送信息 ===========");
System.out.println("headers:" + requestEntity.getHeaders());
System.out.println("body:" + requestEntity.getBody());

ResponseEntity<String> responseEntity = rt.postForEntity(url,requestEntity,String.class);

System.out.println(" ========== 接收信息 ===========");
System.out.println("response body:" + responseEntity.getBody());
System.out.println("response header:" + responseEntity.getHeaders());
```



### 获取请求中formData数据

请求信息：

```
POST /thd/requestparameter/testPost02 HTTP/1.1
Host: 127.0.0.1:8899
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
Cache-Control: no-cache
Postman-Token: cfad4c14-1a9d-fd1e-f2aa-0ed494d1d295

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="name"

devil13th
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="age"

5
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="birthday"

2015_10_10 10:11:12
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="createTime"

1586085091
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

处理数据：

```
@ResponseBody
@RequestMapping(value="/testPost03",method=RequestMethod.POST)
// 表单的body中的数据是以 name=devil13th&age=5 的形式发送,而不是json格式的数据
// 方法参数不要加@ModelAttribute
//url : http://127.0.0.1:8899/thd/requestparameter/testPost03
public Person testPost03(Person person){
    this.log.info("testPost03");
    System.out.println(person);
    return person;
}
```

使用RestTemplate进行请求：

```
 String url = CommonURL.URL +"/testUpload01";

 RestTemplate rt = new RestTemplate();

//设置请求体,注意是LinkedMultiValueMap
MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>();

map.add("age","6");
map.add("name","devil13th");
map.add("createTime","1586085091");
map.add("birthday","2015_10_10 10:11:12");
// 设置请求头内容
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.MULTIPART_FORM_DATA); // 设置content-type 为 multipart/form-data"
headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE); // 设置Accept


HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map,headers);
System.out.println(" ========== 发送信息 ===========");
System.out.println("headers:" + requestEntity.getHeaders());
System.out.println("body:" + requestEntity.getBody());

ResponseEntity<String> responseEntity = rt.postForEntity(url,requestEntity,String.class);

System.out.println(" ========== 接收信息 ===========");
System.out.println("response body:" + responseEntity.getBody());
System.out.println("response header:" + responseEntity.getHea
```



## Date和Timestamp的数据转换

可能你已经发现，在上面的例子中，请求中的日期格式和时间戳的格式各种各样，例如Date格式的数据有`2015_10_10 10:11:12` ,Timestamp格式有`1586085091`和`2012&&12&&21 10$$23$$22` ，SpringBoot是如何将请求中的字符串转换为我们需要的Date或Timestamp类型的数据呢，又或者是我们自定义的一个Java对象，如何转换。

其实在SpringBoot中数据转换只分为两种形式：

1. 一种是JSON格式的字符串转换成某个对象（Json工具负责转换）

2. 一种是非JSON格式的字符串转换成某种类型的数据（Converter负责转换）

### JSON格式的字符串转换成某个对象

SpringBoot中JSON格式的请求数据转换成某个对象默认使用的是Jackson，我们也可以改为fastjson。非基础类型的JSON格式的转换使用的是反序列化器`Deserializer`，将对象转换成JSON字符串使用的是序列化器`Serializer`所以，如果某个对象的属性是非基础类型则转换的功能使用的是Jackson和fastjson中的功能。

#### SpringBoot中使用Jackson进行序列化和反序列化设置

SpringBoot中对于Jackson的序列化和反序列化如下：

创建Date类型序列化器

```
public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
//      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy||MM||dd HH:mm:ss");
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
    }
}
```

创建Date类型反序列化器

```
public class JsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateStr = jsonParser.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }
}

```

创建Timestamp类型的序列化器

```
public class JsonTimestampSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy||MM||dd HH:mm:ss");
        String createTimeStr = sdf.format(new Date(timestamp.getTime()));
        jsonGenerator.writeString(createTimeStr);
    }
}
```



创建Timestamp类型的反序列化器

```
public class JsonTimestampDeserializer extends JsonDeserializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy&&MM&&dd HH$$mm$$ss");
        try{
            return new Timestamp(sdf.parse(value).getTime());
        }catch(Exception e){
            throw new RuntimeException("Timestamp格式错误");
        }
    }
}

```

进行配置，将上面定义好的序列化器和反序列化器添加到Jackson的模块中

```
@Configuration
public class DateTimestampConfig {

    /**
     * 处理jackson反序列化转换器，
     * 用于转换Post请求体的json以及我们的对象序列化为返回响应的json
     * 使用@RequestBody注解的对象中的Date类型将从这里被转换
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class,new JsonDateSerializer());
        javaTimeModule.addDeserializer(Date.class,new JsonDateDeserializer());


        // timestamp的序列化和反序列话
        javaTimeModule.addSerializer(Timestamp.class,new JsonTimestampSerializer());
        javaTimeModule.addDeserializer(Timestamp.class,new JsonTimestampDeserializer());


        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}

```

这样一来我们就可以对时间或其他对象定义请求字符串的数据格式了，例子中仅仅对Date和Timestamp的数据进行了转换，只要创建某个类的序列化和反序列化器就可以实现数据转换了。。

#### Fastjson替换Jackson

我们可以将Fastjson替换Jackson，使用的是SpringBoot中的HTTPMessageConverter，只需进行简单的配置既可

```
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 利用fastjson替换掉jackson,且解决中文乱码问题
     * @param  converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("================ 构建 jsonMessageConverter =====================");
        //1.构建了一个HttpMessageConverter  FastJson   消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.定义一个配置，设置编码方式，和格式化的形式
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        //3.设置成了PrettyFormat格式
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //4.处理中文乱码问题
        List<MediaType> fastMediaTypes =  new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //5.将fastJsonConfig加到消息转换器中
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //converters.add(fastConverter);
        /**
         * SpringBoot 2.0.1版本中加载WebMvcConfigurer
         * 的顺序发生了变动，故需使用converters.add(0, converter);指定
         * FastJsonHttpMessageConverter
         * 在converters内的顺序，否则在SpringBoot 2.0.1及之后的版本中将优先使用Jackson处理
         */
        converters.add(0,fastConverter);
    }


    /**
     * 配置FastJson为Spring Boot默认JSON解析框架
     * @return  HttpMessageConverters
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        // 1.定义一个converters转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        // 3.在converter中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 4.将converter赋值给HttpMessageConverter
//        HttpMessageConverter<?> converter = fastConverter;
//        // 5.返回HttpMessageConverters对象
//        return new HttpMessageConverters(converter);
//    }

}
```

将Jackson替换成Fastjson后也可以自定义某种类型的序列化和反序列化器，对某种类型的数据自动转换成某个对象。这里不赘述了。

### 非JSON格式的字符串转换成某个对象

SpringBoot中对于非JSON格式的转换使用的是`Converter`，我们可以针对某一个对象自己定义一个Converter，SpringBoot接收数据后如果发现转换的目标是某个对象会去找我们制定的Converter。下面是定义和配置Converter的步骤

#### SpringBoot中Converter设置

创建针对Date的Converter

```
public class DateConverter implements Converter<String, Date> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String dateFormat = "yyyy_MM_dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy_MM_dd";
    private static final String timestampFormat = "^\\d+$";
    @Override
    public Date convert(String value) {
        logger.info(" 转换日期 :" + value);

        if(value == null || value.trim().equals("") || value.equalsIgnoreCase("null")){
            return null;
        }

        value = value.trim();

        try{
            if(value.contains("_")){
                SimpleDateFormat sdf;

                if(value.contains(":")){ // 长日期 yyyy_MM_dd HH:mm:ss
                    sdf = new SimpleDateFormat(dateFormat);
                }else{   // 短日期 yyyy_MM_dd
                    sdf = new SimpleDateFormat(shortDateFormat);
                }
                return sdf.parse(value);
            }else if(value.matches(timestampFormat)){ // 时间戳 1585875084
                Long timestamp = new Long(value);
                return new Date(timestamp);
            }
        }catch(Exception e){
            throw new RuntimeException(String.format(" parser %s to Date Failed",value));
        }

        throw new RuntimeException(String.format(" parser %s to Date Failed",value));
    }
}
```

创建针对Timestamp的Converter

```
public class TimestampConverter implements Converter<String, Timestamp> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String timestampFormat = "^\\d+$";
    @Override
    public Timestamp convert(String value) {
        logger.info(" 转换时间戳 :" + value);

        if(value == null || value.trim().equals("") || value.equalsIgnoreCase("null")){
            return null;
        }

        value = value.trim();

        try{
            if(value.matches(timestampFormat)){
                Long timestamp = new Long(value);
                return new Timestamp(timestamp);
            }else{
                throw new RuntimeException(String.format(" parser %s to Date Failed",value));
            }
        }catch(Exception e){
            throw new RuntimeException(String.format(" parser %s to Date Failed",value));
        }


    }
}

```

配置Converter

```
@Configuration
public class DateTimestampConfig {
    /**
     * Date转换器, 用于转换@RequestParam和@PathVariable修饰的参数
     */
    @Bean
    public Converter<String, Date> dateConverter(){
        return new DateConverter();
    }
    /**
     * 时间戳转换器 , 用于转换@RequestParam和@PathVariable修饰的参数
     * @return
     */
    @Bean
    public Converter<String, Timestamp> timestampConverter(){
        return new TimestampConverter();
    }
}
```

