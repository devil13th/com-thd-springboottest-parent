package com.thd.springboottest.requestparameter.test;

import com.alibaba.fastjson.JSONObject;
import com.thd.springboottest.requestparameter.entity.Person;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * com.thd.springboottest.requestparameter.test.test01GetParameter
 *
 * 该类作用：
 * 1.测试RequestparameterController 对应的URL
 * 2.restTemplate的使用
 * @author: wanglei62
 * @DATE: 2020/1/20 6:54
 **/
public class test02PostParameter {

    /**
     * post方法请求
     * 数据在请求的Body中
     * @param args
     */

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
    }
}
