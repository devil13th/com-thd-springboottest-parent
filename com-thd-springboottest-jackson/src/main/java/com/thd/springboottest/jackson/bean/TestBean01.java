package com.thd.springboottest.jackson.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.thd.springboottest.jackson.bean.TestBean01
 *
 * @author: wanglei62
 * @DATE: 2020/4/1 10:46
 **/
@Data
public class TestBean01 {
    private String name;
    private Integer age;
    private Date birthday;
    private Timestamp createTime;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthdayStr = sdf.format(birthday);
        String createTimeStr = sdf.format(new Date(createTime.getTime()));

        return "TestBean01{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthdayStr +
                ", createTime=" + createTimeStr +
                '}';
    }
}
