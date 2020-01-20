package com.thd.springboottest.requestparameter.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.requestparameter.test.test01GetParameter
 *
 * 该类作用：
 * 1.测试RequestparameterController 对应的URL
 * 2.restTemplate的使用
 * @author: wanglei62
 * @DATE: 2020/1/20 6:54
 **/
public class test03PostParameter {

    /**
     * post方法请求
     * post表单请求，xxx_form_urlencoded方式传输
     * @param args
     */

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
}
