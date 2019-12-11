package com.thd.springboottest.exceptionhandler.exceptionbean;

import com.thd.springboottest.exceptionhandler.bean.MessageEnum;

/**
 * com.thd.springboottest.exceptionhandler.exceptionbean.MyExceptionParent
 * User: devil13th
 * Date: 2019/11/24
 * Time: 1:08
 * Description: No Description
 */
public class MyParentException extends RuntimeException {
    private Integer code;
    private String message;
    public MyParentException(MessageEnum messageEnum) {
        super(messageEnum.getMessage());
        this.message = messageEnum.getMessage();
        this.code = messageEnum.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
