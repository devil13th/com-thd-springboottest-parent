package com.thd.springboottest.jackson.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 序列化日期方式
 * 添加 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")来解决日期格式问题
 *
 * @author: wanglei62
 * @DATE: 2020/4/1 10:46
 **/
@Data
public class TestBean02 {
    private String name;
    private Integer age;
    @JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;
    @JsonFormat(pattern = "yyyy**MM**dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthdayStr = sdf.format(birthday);
        String createTimeStr = sdf.format(new Date(createTime.getTime()));

        return "TestBean02{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthdayStr +
                ", createTime=" + createTimeStr +
                '}';
    }
}
