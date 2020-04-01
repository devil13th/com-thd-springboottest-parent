package com.thd.springboottest.jackson.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class TestBean03 {
    private String name;
    private Integer age;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date birthday;
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @JsonDeserialize(using = JsonTimestampDeserializer.class)
    private Timestamp createTime;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthdayStr = sdf.format(birthday);
        String createTimeStr = sdf.format(new Date(createTime.getTime()));

        return "TestBean03{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthdayStr +
                ", createTime=" + createTimeStr +
                '}';
    }
}
