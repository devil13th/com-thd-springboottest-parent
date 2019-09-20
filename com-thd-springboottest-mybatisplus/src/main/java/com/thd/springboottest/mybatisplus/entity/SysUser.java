package com.thd.springboottest.mybatisplus.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author devil13th
 **/
@Data
public class SysUser {
    private String userId;
    private String userName;
    private int userSex;
    private String userMail;
    private String userTel;
    private Date userBirthday;
    private String userStatus;
    private String orgId;

}
