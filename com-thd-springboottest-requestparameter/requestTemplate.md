[TOC]

# RestTemplate接口请求总结

# 一、获取接口返回状态码

使用getForEntity调用接口，返回结果调用getStatusCode()方法取得HttpStatus对象，然后就可以调用里面的各种方法来满足你的需求了



```kotlin
//判断接口返回是否为200
    public static Boolean ping(){
        String url = domain + "/it/ping";
        try{
            ResponseEntity<String> responseEntity = template.getForEntity(url,String.class);
            HttpStatus status = responseEntity.getStatusCode();//获取返回状态
            return status.is2xxSuccessful();//判断状态码是否为2开头的
        }catch(Exception e){
            return false; //502 ,500是不能正常返回结果的，需要catch住，返回一个false
        }
    }
```

# 二、什么都不带，将参数拼接在请求url后面

将参数拼接在请求url后面，postForObject请求参数为null



```dart
public static void login(String userCode,String md5Password,int companyId,WebDriver driver){
        RestTemplate template = new RestTemplate();
        String url = "https://login.dooioo.net/api/autotest/userLogin";//请求地址
        String param ="?userCode=" + userCode + "&md5Password=" + md5Password + "&companyId=" + companyId;
        try{
                String  str = template.postForObject(url+param,null, String.class);//所得结果为调整成String类型
           }catch(Exception e){
                System.out.println("登录失败");
                e.printStackTrace();
            }
    }
```

# 三、带cookie，header，参数

带cookie实际也是将参数塞入header中:

1、定义header对象： HttpHeaders headers = new HttpHeaders()
 2、将要的cookie塞入header：headers.put(HttpHeaders.COOKIE,cookieList)（注意cookie需要是一个list，内容为 name=value 例如：loginticket=sldjfas112sadfsd）
 3、也可以在Header中塞入其他值：headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)



```dart
/*
        获取浏览器的cookie，将其塞入header中
     */
    public static HttpHeaders getHeader(WebDriver driver){
        HttpHeaders headers = new HttpHeaders();
        Set<Cookie> cookies = driver.manage().getCookies();//获取浏览器cookies
        List<String> cookieList = new ArrayList<String>();
        for(Cookie cookie:cookies){ //将浏览器cookies放入list中
            //System.out.println("当前cookies为:" +  cookie.getDomain() + " " + cookie.getName() + ":" + cookie.getValue());
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }
        //System.out.println("cookie为：" + cookieList.toString());
        headers.put(HttpHeaders.COOKIE,cookieList); //将cookie放入header
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //post表单 ，如果是个json则设置为MediaType.APPLICATION_JSON
        return headers;
    }
    
```

放入参数：

1、使用MultiValueMap用来放参数，（使用HashMap不行,具体原因可见[http://www.cnblogs.com/shoren/p/RestTemplate-problem.html](https://link.jianshu.com?t=http://www.cnblogs.com/shoren/p/RestTemplate-problem.html) ），
 2、根据header和参数实例化HttpEntity ,然后调用postForEntity方法调用接口



```dart
HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String,String>>(param,headers)
```

完整代码如下（使用方法可参考：[https://segmentfault.com/a/1190000007778403](https://link.jianshu.com?t=https://segmentfault.com/a/1190000007778403)）：



```dart
    public static void gitUpLoad(WebDriver driver,String uploadUrl,String fileName,String content,String branch,String requestUrl) throws  Exception{

        String authenticity_token = getToken(driver,uploadUrl);//获取token
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = getHeader(driver);//获取header
        MultiValueMap<String,String> param = new LinkedMultiValueMap<String, String>();//参数放入一个map中，restTemplate不能用hashMap
        //将请求参数放入map中
        param.add("authenticity_token",authenticity_token);
        param.add("file_name",fileName);
        param.add("encoding","text");
        param.add("commit_message","addFile");
        param.add("target_branch",branch);
        param.add("original_branch",branch);
        param.add("content",content);
        param.add("utf8","✓");
        //System.out.println("参数内容为：" + param.toString());
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<MultiValueMap<String,String>>(param,headers);//将参数和header组成一个请求
        ResponseEntity<String> response = template.postForEntity(requestUrl, request, String.class);

    }
```

# 四、使用exchange指定调用方式

使用exchange方法可以指定调用方式

需要注意的一点是对于返回结果为204 no content,这种没有返回值的请求，RestTemplate会抛错，有需要的话可以使用httpClient的fluent



```dart
public  void  deleteQueue(String vhost,String queue){

        HttpHeaders headers = new HttpHeaders();//header参数
        headers.add("authorization",Auth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject content = new JSONObject();//放入body中的json参数
        content.put("mode","delete");
        content.put("name",queue);
        content.put("vhost","/" + vhost);

        String newVhost = "%2F" + vhost;
        String newUrl = url + "/api/queues/" + newVhost + "/" + queue;

        HttpEntity<JSONObject> request = new HttpEntity<>(content,headers); //组装
  
        ResponseEntity<String> response = template.exchange(newUrl,HttpMethod.DELETE,request,String.class);
    }
```



# 例子

## 服务端

```
package com.thd.springboottest.requestparameter.controller;


import com.thd.springboottest.requestparameter.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.List;

/**
 * @author devil13th
 *
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 *
 **/
@Controller
@RequestMapping(value="/requestparameter")
public class RequestparameterController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/testGet01",method=RequestMethod.GET)
    //直接获取url?后面对应的参数
    //url : http://127.0.0.1:8899/thd/requestparameter/testGet01?usr=devil13th&pwd=123456
    public String testGet01(@RequestParam String usr,@RequestParam String pwd){
        this.log.info("testGet01");
        System.out.println(usr + "," + pwd);
        return (usr + "," + pwd);
    }


    @ResponseBody
    @RequestMapping(value="/testGet02/{str}",method=RequestMethod.GET)
    // 通过在url中使用{}占位符来获取参数
    //url : http://127.0.0.1:8899/thd/requestparameter/testGet02/hello
    public String testGet02(@PathVariable String str ){
        this.log.info("testGet02");
        System.out.println(str);
        return str;
    }


    @ResponseBody
    @RequestMapping(value="/testGet03",method=RequestMethod.GET)
    // url中?后的参数直接转对象接收
    //url : http://127.0.0.1:8899/thd/requestparameter/testGet03?age=5&name=devil13th
    public Person testGet03(Person person ){
        this.log.info("testGet03");
        System.out.println(person);
        return person;
    }


    @ResponseBody
    @RequestMapping(value="/testPost01",method=RequestMethod.POST)
    //通过Post方式提交来body内容(一般是json),可以通过@RequestBody直接将body中的json转成对象
    //注意这种方式要设置头部信息的contentType属性 headers.set('Content-Type', 'application/json');
    public Person testPost01(@RequestBody Person person){
        this.log.info("testPost01");
        System.out.println(person);
        return person;
    }

    @ResponseBody
    @RequestMapping(value="/testPost02",method=RequestMethod.POST)
    // form 表单提交接收参数需要使用@ModelAttribute("person"),
    // 表单中的name不要加person前缀，例如name="person.id"应写成name="id"
    public Person testPost02(@ModelAttribute("person") Person person){
        this.log.info("testPost02");
        System.out.println(person);
        return person;
    }

    @ResponseBody
    @RequestMapping(value="/testUpload01",method=RequestMethod.POST)
    //单个文件上传 并获取数据
    public String testUpload01(@RequestParam("file") MultipartFile file,@ModelAttribute("person") Person person) throws IOException {
        System.out.println(person);
        System.out.println("文件大小:" + file.getSize());
        InputStream is = file.getInputStream();
        byte[] buffer = new byte[1024 * 2 ];

        OutputStream os = new FileOutputStream(new File("D://deleteme//testfile"));
        while(is.read(buffer) != -1){
            os.write(buffer);
        }
        os.close();
        is.close();

        return "SUCCESS";
    }



    @ResponseBody
    @RequestMapping(value="/testUpload02",method=RequestMethod.POST)
    //多个附件上传
    public String handleFormsUpload(HttpServletRequest request) {
        try{
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            MultipartFile file = null;
            for(int i = 0; i < files.size(); ++i){
                file = files.get(i);
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    File picture = new File("D://deleteme//"+ String.valueOf(i) + ".png");//这里指明上传文件保存的地址
                    FileOutputStream fos = new FileOutputStream(picture);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    bos.write(bytes);
                    bos.close();
                    fos.close();
                }
            }

            return "success";

        }catch (IOException e){
            System.out.println(e);
        }
        return "failed";
    }


    @ResponseBody
    @RequestMapping(value="/testHeader01",method=RequestMethod.GET)
    //获取请求头内容
    public String testHeader01(@RequestHeader(name="myHeader") String myHeader) throws IOException {
        String str = " Request Header - myHeadeer:" + myHeader;
        System.out.println(str);
        return str;
    }



}

```

## RestTemplate测试

### 创建一个支持Https的工厂类

SSL.java

```
package com.thd.springboottest.requestparameter.test;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * com.thd.springboottest.requestparameter.test.SSL
 *
 * @author: wanglei62
 * @DATE: 2020/1/20 6:57
 **/
public class SSL extends SimpleClientHttpRequestFactory {
    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod)
            throws IOException {
        if (connection instanceof HttpsURLConnection) {
            prepareHttpsConnection((HttpsURLConnection) connection);
        }
        super.prepareConnection(connection, httpMethod);
    }

    private void prepareHttpsConnection(HttpsURLConnection connection) {
        connection.setHostnameVerifier(new SkipHostnameVerifier());
        try {
            connection.setSSLSocketFactory(createSslSocketFactory());
        }
        catch (Exception ex) {
            // Ignore
        }
    }

    private SSLSocketFactory createSslSocketFactory() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[] { new SkipX509TrustManager() },
                new SecureRandom());
        return context.getSocketFactory();
    }

    private class SkipHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }

    }

    private static class SkipX509TrustManager implements X509TrustManager {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

    }

}

```

### 测试内容

都放到一起了，每个main函数测试服务端的一个方法

```

public class test01GetParameter {
    /**
     * get方法请求
     * 数据为url?后面的内容或restful路径参数
     * @param args
     */

    public static void main(String args[]){
//        String url = CommonURL.URL +"/testGet01?usr=devil13th&pwd=123456";
//        String url = CommonURL.URL +"/testGet02/hello";
        String url = CommonURL.URL +"/testGet03?age=5&name=devil13th";


        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);

        ResponseEntity<String> responseEntity = rt.getForEntity(url,String.class);

        System.out.println(" ========== 接收信息 ===========");
        System.out.println("response body:" + responseEntity.getBody());
        System.out.println("response header:" + responseEntity.getHeaders());
    }
}

====================================================================
public static void main(String args[]){
        String url = CommonURL.URL +"/testPost01";
        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);
//        Person  p = new Person();
//        p.setAge(5);
//        p.setName("张三");

        // 请求体内容
        JSONObject obj = new JSONObject();
        obj.put("age","5");
        obj.put("name","devil13th");

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
    }
====================================================================
public static void main(String args[]){
        String url = CommonURL.URL +"/testPost02";
        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);
        // 请求体内容
        MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
        map.add("age","6");
        map.add("name","devil13th");

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
    }
====================================================================
public static void main(String args[]){
        String url = CommonURL.URL +"/testUpload01";
        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);
        //设置请求体,注意是LinkedMultiValueMap
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>();
        FileSystemResource fileSystemResource = new FileSystemResource("D:\\deleteme\\application.yml");
        map.add("file", fileSystemResource);
        map.add("filename","kkk");
        map.add("age","6");
        map.add("name","devil13th");

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
        System.out.println("response header:" + responseEntity.getHeaders());
    }
====================================================================
public static void main(String args[]){
        String url = CommonURL.URL +"/testUpload02";
        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);

        //设置请求体,注意是LinkedMultiValueMap
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>();
        FileSystemResource fileSystemResource1 = new FileSystemResource("D:\\deleteme\\application.yml");
        FileSystemResource fileSystemResource2 = new FileSystemResource("D:\\deleteme\\anyproxy_r.js");

        map.add("file", fileSystemResource1);
        map.add("file", fileSystemResource2);
        map.add("filename","kkk");
        map.add("age","6");
        map.add("name","thd");

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
        System.out.println("response header:" + responseEntity.getHeaders());
    }
====================================================================
 public static void main(String args[]){
        String url = CommonURL.URL +"/testHeader01";

        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);

        // 设置请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.TEXT_PLAIN_VALUE);
        headers.add("myHeader", "hello devil13th");
        List<String> cookieList = new ArrayList<>();
        cookieList.add("token=a9aeb164398d1f8d55c7be0c43db08444dcfdced5f");
        headers.put("Cookie", cookieList);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);


        ResponseEntity<String> responseEntity = rt.exchange(url, HttpMethod.GET, requestEntity, String.class);

        System.out.println(" ========== 接收信息 ===========");
        System.out.println("response body:" + responseEntity.getBody());
        System.out.println("response header:" + responseEntity.getHeaders());
    }


```

