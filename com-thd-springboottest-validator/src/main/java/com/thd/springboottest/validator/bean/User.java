package com.thd.springboottest.validator.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * com.thd.springboottest.standardcode.bean.User
 *
 * @author: wanglei62
 * @DATE: 2020/5/6 9:18
 **/
@Data
public class User {
    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Range(min=18,max=50,message = "年龄必须在18到50之间")
    @Max(value = 50,message = "年龄不能超过50岁")
    @Min(value = 18,message = "年龄不能小于18岁")
    private Integer age;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String idCard;

    @Pattern(regexp = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$", message = "电话格式错误")
    private String tel;
}
