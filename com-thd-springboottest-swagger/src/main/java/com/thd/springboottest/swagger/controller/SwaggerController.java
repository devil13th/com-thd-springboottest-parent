package com.thd.springboottest.swagger.controller;

import com.thd.springboottest.swagger.entity.MyBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.swagger.controller.SwaggerController
 *
 * @author: wanglei62
 * @DATE: 2019/10/23 18:31
 **/
@Controller
@RequestMapping("/swagger")
public class SwaggerController {
    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/swagger/test?name=thd&age=5
    @ApiOperation(value = "测试接口", notes = "用于测试模块是否可以正常启动", httpMethod = "ALL")
    public ResponseEntity<MyBean> test(
            @ApiParam(name="姓名",value="字符串格式",required=false) String name,
            @ApiParam(name="年龄",value="字符串格式",required=false) int age){
        MyBean mb = new MyBean();
        mb.setName(name);
        mb.setAge(age);
        return ResponseEntity.ok(mb);
    }


    @PostMapping("testPost")
    // url : http://127.0.0.1:8899/thd/swagger/testPost
    @ApiOperation(value = "测试接口", notes = "用于测试模块是否可以正常启动", httpMethod = "ALL")
    public ResponseEntity<MyBean> testPost(@ApiParam(name="参数",value="json字符串",required=true) @RequestBody MyBean mybean){
        return ResponseEntity.ok(mybean);
    }
}
