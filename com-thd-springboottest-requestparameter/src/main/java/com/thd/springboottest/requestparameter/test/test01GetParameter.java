package com.thd.springboottest.requestparameter.test;

import com.alibaba.fastjson.JSONObject;
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
public class test01GetParameter {

    /**
     * get方法请求
     * 数据为url?后面的内容或restful路径参数
     * @param args
     */

    public static void main(String args[]){

        //String url = CommonURL.URL +"/testGet01?usr=devil13th&pwd=123456&birthday=2015_10_10 10:11:12&createTime=1586085091";
//        String url = CommonURL.URL +"/testGet01a?name=devil13th&age=15&birthday=2015_10_10 10:11:12&createTime=1586085091";
        String url =  CommonURL.URL +"/testGet02/2015_01_01/1586085091";
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
