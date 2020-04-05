package com.thd.springboottest.requestparameter.test;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class test04PostUpload {

    /**
     * post方法请求
     * post上传文件
     * @param args
     */

    public static void main(String args[]){
        String url = CommonURL.URL +"/testUpload01";
        SSL factory = new SSL();
        factory.setReadTimeout(100000);
        factory.setConnectTimeout(15000);
        RestTemplate rt = new RestTemplate(factory);






        //设置请求体,注意是LinkedMultiValueMap
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>();
        FileSystemResource fileSystemResource = new FileSystemResource("D:\\deleteme\\testfile");
        map.add("file", fileSystemResource);
        map.add("filename","kkk");
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
        System.out.println("response header:" + responseEntity.getHeaders());
    }
}
