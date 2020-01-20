package com.thd.springboottest.requestparameter.test;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * com.thd.springboottest.requestparameter.test.test01GetParameter
 *
 * 该类作用：
 * 1.测试RequestparameterController 对应的URL
 * 2.restTemplate的使用
 * @author: wanglei62
 * @DATE: 2020/1/20 6:54
 **/
public class test061GetHeaderParameter {

    /**
     * get方法请求
     * 数据为url?后面的内容或restful路径参数
     * @param args
     */

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
}
