package com.thd.springboottest.requestparameter.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.thd.springboottest.requestparameter.serializer.fastjson.FastjsonDateDeserializer;
import com.thd.springboottest.requestparameter.serializer.fastjson.FastjsonDateSerializer;
import com.thd.springboottest.requestparameter.serializer.fastjson.FastjsonTimestampDeserializer;
import com.thd.springboottest.requestparameter.serializer.fastjson.FastjsonTimestampSerializer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.thd.springboottest.requestparameter.entity.Person
 * User: devil13th
 * Date: 2019/9/30
 * Time: 0:14
 * Description: No Description
 */
public class Person {
    private String name;
    private int age;

    /**
     * fastjson用到的序列化器和反序列化器
     */
    @JSONField(serializeUsing = FastjsonDateSerializer.class,deserializeUsing = FastjsonDateDeserializer.class)
    private Date birthday;
    @JSONField(serializeUsing = FastjsonTimestampSerializer.class,deserializeUsing = FastjsonTimestampDeserializer.class)
    private Timestamp createTime;
    public Person() {

    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String birthdayStr = "";
        if(this.getBirthday() != null){
            sdf.format(this.birthday);
        }

        String createTimeStr = "";
        if(this.createTime != null ){
            createTimeStr = sdf.format(new Date(this.createTime.getTime()));
        }
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthdayStr +
                ", createTime=" + createTimeStr +
                '}';
    }
}
