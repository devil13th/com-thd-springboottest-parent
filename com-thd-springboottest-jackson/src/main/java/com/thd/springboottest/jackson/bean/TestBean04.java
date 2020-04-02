package com.thd.springboottest.jackson.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 序列化日期方式
 * 添加 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")来解决日期格式问题
 *
 * @author: wanglei62
 * @DATE: 2020/4/1 10:46
 **/
@Data
public class TestBean04 {
    private String name;
    private Integer age;
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime birthday;


    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String  birthdayStr = birthday.format(dateTimeFormatter);
        return "TestBean03{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthdayStr +
                '}';
    }
}
