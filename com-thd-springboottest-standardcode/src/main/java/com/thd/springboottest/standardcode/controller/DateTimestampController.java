package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.bean.Result;
import com.thd.springboottest.standardcode.controller.parent.BasicController;
import com.thd.springboottest.standardcode.entity.MyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.standardcode.controller.DateTimestampController
 * User: devil13th
 * Date: 2019/11/24
 * Time: 17:33
 * Description: No Description
 */
@Controller
@RequestMapping("/datetimestamp")
public class DateTimestampController extends BasicController {


    @RequestMapping("/send")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/datetimestamp/send
    public Result send(){
        this.getLog().info(" send() ");
        MyUser myUser = new MyUser();
        myUser.setUserAge(5);
        myUser.setUserName("devil13th");
        myUser.setUserBirthday(new Date());
        myUser.setUserCreateTime(new Timestamp(new Date().getTime()));
        myUser.setUserId("1234");
        return Result.success(myUser);
    }


    @RequestMapping("/receive")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/datetimestamp/receive?id=1234&userBirthday=2019-07-16&userName=devil13th&userAge=5&userCreateTime=2019-07-17 07:45:27
    public Result receive(MyUser myUser){
        this.getLog().info(" receive() ");
        this.getLog().info(myUser.toString());
        return Result.success(myUser);
    }

}
