package com.thd.springboottest.redistemplate.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thd.springboottest.redistemplate.serializer.JsonDateDeserializer;
import com.thd.springboottest.redistemplate.serializer.JsonDateSerializer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author devil13th
 **/
public class User implements Serializable {
    private String userId;
    private String userName;
    private Integer userAge;
//    @JsonSerialize(using = JsonDateSerializer.class)
//    @JsonDeserialize(using = JsonDateDeserializer.class)
//    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date userBirthday;

    private Item item;
    private List<Item> itemList;
    private List<User> children;

    private LocalDateTime ldt;
    private LocalDate ld;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp userCreateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Timestamp getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Timestamp userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDate getLd() {
        return ld;
    }

    public void setLd(LocalDate ld) {
        this.ld = ld;
    }

    public List<User> getChildren() {
        return children;
    }

    public void setChildren(List<User> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userBirthday=" + userBirthday +
                ", userCreateTime=" + userCreateTime +
                '}';
    }


    public static User getInstance(){
        User u = new User();
        u.setUserAge(5);
        u.setUserBirthday(new Date());
        u.setUserId("1");
        u.setUserName("devil13th");
        u.setUserCreateTime(new Timestamp(new Date().getTime()));
        u.setLd(LocalDate.now());
        u.setLdt(LocalDateTime.now());

        Item item = new Item();
        item.setId(30);
        item.setName("mama");
        item.setType("family");
        u.setItem(item);
        return u;
    }
}
