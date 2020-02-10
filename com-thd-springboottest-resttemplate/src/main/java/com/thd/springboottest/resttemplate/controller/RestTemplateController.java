package com.thd.springboottest.resttemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thd.springboottest.resttemplate.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.thd.springboottest.resttemplate.controller.SendController
 *
 * @author: wanglei62
 * @DATE: 2020/2/4 12:34
 **/
@Controller
@RequestMapping(value="/restTemplate")
public class RestTemplateController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/testGet01")
    @ResponseBody
    /**
     * @see ReceiveController#testGet01()
     * Get 方式访问任意地址 , 服务端返回的是字符串 (也可以是json或其他任意字符串)
     */
    // url : http://127.0.0.1:8899/thd/restTemplate/testGet01
    public ResponseEntity testGet01(){
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        String url = "https://www.baidu.com";
        // url = "http://127.0.0.1:8899/thd/receive/testGet01";

        ResponseEntity<String> entity =  rt.getForEntity(url,String.class);
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());

        System.out.println(" =======================  ");

        ResponseEntity<String> res =  rt.getForEntity("http://127.0.0.1:8899/thd/receive/testGet01",String.class);
        System.out.println("响应头:" + res.getHeaders());
        System.out.println("响应体:" + res.getBody());
        System.out.println("响应状态码:" + res.getStatusCode());

        return ResponseEntity.ok(entity.getBody());
    }
    /**
     * @see ReceiveController#testGet02()
     * 服务端返回JSON
     */
    @RequestMapping("/testGet02")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testGet02
    public ResponseEntity testGet02(){
        Person p = new Person();
        p.setAge(1);
        p.setName("testGet02");

        // 使用restTemplate.getForEntity发送get请求
        ResponseEntity<Person> entity = restTemplate.getForEntity("http://127.0.0.1:8899/thd/receive/testGet02",Person.class);
        System.out.println("响应头:" + entity.getHeaders());
        System.out.println("响应体:" + entity.getBody());
        System.out.println("响应状态码:" + entity.getStatusCode());
        System.out.println(entity.getStatusCodeValue());

        System.out.println(" ============================= ");

        // 使用restTemplate.getForObject发送get请求
        Person result = restTemplate.getForObject("http://127.0.0.1:8899/thd/receive/testGet02",Person.class);
        System.out.println("响应体：" + result);

        return ResponseEntity.ok(result);
    }
    /**
     * @see ReceiveController#testGet03(String, String)
     * Get 方式 url中带有参数
     */
    @RequestMapping("/testGet03")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testGet03
    public ResponseEntity testGet03(){
        Person p = new Person();
        p.setAge(1);
        p.setName("test01");
        Person result =  restTemplate.getForObject("http://127.0.0.1:8899/thd/receive/testGet03?usr=devil13th&pwd=123456",Person.class);
        return ResponseEntity.ok(result);
    }

    /**
     * @see ReceiveController#testGet04(String)
     */
    @RequestMapping("/testGet04")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testGet04
    // Get方式请求参数包含在路径中
    public ResponseEntity testGet04(){
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> entity =  rt.getForEntity("http://127.0.0.1:8899/thd/receive/testGet04/hello",String.class);
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCode());

        return ResponseEntity.ok(entity.getBody());
    }

    /**
     * @see ReceiveController#testPost01(Person)
     * 请求发送JSON数据，返回的也是JSON数据
     * 服务端是 @RequestBody 注释参数
     */
    @RequestMapping("/testPost01")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testPost01
    public ResponseEntity testPost01(){

        String url = "http://127.0.0.1:8899/thd/receive/testPost01";
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        // 请求体
        Person p = new Person();
        p.setName("devil13th");
        p.setAge(2);
        p.setBirthday(new Date());
        HttpEntity<Person> httpEntity = new HttpEntity<Person>(p,headers);

        System.out.println(" ========== 发送信息 ===========");
        System.out.println("headers:" + httpEntity.getHeaders());
        System.out.println("body:" + httpEntity.getBody());
        // 使用postForEntity方法
        ResponseEntity<String> res =  restTemplate.postForEntity(url,httpEntity,String.class);
        System.out.println(" ========== 接收信息 ===========");
        System.out.println("body:" + res.getBody());
        System.out.println("header:" + res.getHeaders());
        System.out.println("================================");
        // 使用postForObject方法
        Person result = restTemplate.postForObject(url,httpEntity,Person.class);
        System.out.println(" ========== 接收信息 ===========");
        System.out.println("接收信息:" + result);

        return ResponseEntity.ok("SUCCESS");
    }

    /**
     * @see ReceiveController#testPost02(Person)
     * 服务端是 @ModelAttribute 注释参数
     */
    @RequestMapping("/testPost02")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testPost02
    public ResponseEntity testPost02(){

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        List<MediaType> accepts = new ArrayList<MediaType>();
        accepts.add(MediaType.APPLICATION_JSON);
        accepts.add(MediaType.APPLICATION_JSON_UTF8);
        accepts.add(MediaType.TEXT_PLAIN);
        headers.setAccept(accepts);
        // 请求体 - 模拟formData
        MultiValueMap formData = new LinkedMultiValueMap();
        formData.add("name","zhangsan");
        formData.add("age","4");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(formData,headers);

        System.out.println(" ========== 发送信息 ===========");
        System.out.println("headers:" + httpEntity.getHeaders());
        System.out.println("body:" + httpEntity.getBody());


        String url = "http://127.0.0.1:8899/thd/receive/testPost02";


        RestTemplate rt = new RestTemplate();
        // 设置消息转换器为 String类型转换器
        // 当服务端以非@RequestBody注释接收的时候，要使用StringHttpMessageConverter，不能用fastjson, 因为服务端接收数据格式非JSON!
        rt.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 使用postForEntity方法
        ResponseEntity<String> res = rt.postForEntity(url, httpEntity, String.class);
        System.out.println(" ========== 接收信息 ===========");
        System.out.println(res);
        System.out.println("====================================");
        // 使用postForObject方法
        Person p =  rt.postForObject(url,httpEntity,Person.class);
        System.out.println("响应结果："+p);

        return ResponseEntity.ok("SUCCESS");
    }

    /**
     * @see ReceiveController#testPost03(Person)
     */
    @RequestMapping("/testPost03")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testPost03
    public ResponseEntity testPost03(){

        String url = "http://127.0.0.1:8899/thd/receive/testPost03";
        HttpMethod method = HttpMethod.POST;

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        HttpEntity<String> httpEntity = new HttpEntity<String>("age=6&name=lisi",headers);

        // 当服务端以非@ResponseBody注释接收的时候，要使用StringHttpMessageConverter，不能用fastjson, 因为服务端接收数据格式非JSON!
        RestTemplate rt = new RestTemplate();
        // 设置消息转换器为 String类型转换器 - 不设置也可以，因为默认的String转换器在JSON转换器前面
        // 但如果使用fastjson,要设置String转换器在FastJson转换器前面
        rt.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> res = rt.exchange(url,method,httpEntity,String.class);
        System.out.println(" ========== 接收信息 ===========");
        System.out.println(res);
        System.out.println("body:" + res.getBody());
        System.out.println("header:" + res.getHeaders());


        System.out.println("========================");

        return ResponseEntity.ok("SUCCESS");
    }




    /**
     * @see ReceiveController#testDownload(HttpServletResponse) 
     */
    @RequestMapping("/testDownload")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testDownload
    public ResponseEntity testDownload(){
        RestTemplate rt = new RestTemplate();
        String url = "http://127.0.0.1:8899/thd/receive/testDownload";
        HttpMethod method = HttpMethod.POST;

        ResponseEntity<byte[]> response =  rt.getForEntity(url,byte[].class);
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());

        System.out.println(response.getHeaders().getContentType());
        //System.out.println(response.getHeaders().getContentType().getSubtype());
        try {
            File file = File.createTempFile("ess-", "." + "pdf");
            System.out.println(file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(response.getBody());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * @see ReceiveController#testUpload(MultipartFile, Person)
     */
    @RequestMapping("/testUpload")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/restTemplate/testUpload
    public ResponseEntity testUpload(){
        RestTemplate rt = new RestTemplate();
        String url = "http://127.0.0.1:8899/thd/receive/testUpload";
        HttpMethod method = HttpMethod.POST;


        RestTemplate restTemplate = new RestTemplate();

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        FileSystemResource fileSystemResource = new FileSystemResource("D:\\deleteme\\target\\2020-02-10_01-56-15_auditDOC_72780405daba4c248fa512ad926ec49c.pdf");
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        form.add("filename","aa.pdf");
        form.add("name","zhangsan");
        form.add("age","4");


        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(form, headers);

        String s = restTemplate.postForObject(url, entity, String.class);
        System.out.println("响应结果:" + s);


        return ResponseEntity.ok("SUCCESS");
    }

}
