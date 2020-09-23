package com.thd.springboottest.shiro.bean;


import com.thd.springboottest.shiro.constants.CommonConstants;

import java.io.Serializable;

/**
 * com.thd.springboot.framework.model.Message
 *
 * @author: wanglei62
 * @DATE: 2020/1/20 18:02
 **/

public class Message<T> implements Serializable {

    private static final long serialVersionUID = 6407243559651442287L;

    /**
     * 0为正常返回，>0为业务错误,<0为系统错误
     */
    private String code;

    /**
     * 提示msg
     */
    private String msg;

    /**
     * 内容
     */
    private T result;

    public Message(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Message(String code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }


    /**
     * 成功消息
     *
     * @param msg
     * @param result
     * @return
     */
    public static Message success(String msg, Object result) {
        return new Message(CommonConstants.STATUS_SUCCESS_CODE, msg, result);
    }

    public static Message success(Object result) {
        return new Message(CommonConstants.STATUS_SUCCESS_CODE, CommonConstants.STATUS_SUCCESS, result);
    }

    public static Message success() {
        return new Message(CommonConstants.STATUS_SUCCESS_CODE, CommonConstants.STATUS_SUCCESS);
    }

    public static Message success(String msg) {
        return new Message(CommonConstants.STATUS_SUCCESS_CODE, msg);
    }

    /**
     * 失败消息
     *
     * @return
     */
    public static Message error() {
        return new Message(CommonConstants.STATUS_ERROR_CODE, CommonConstants.STATUS_ERROR);
    }

    /**
     * 失败消息
     *
     * @param msg
     * @return
     */
    public static Message error(String msg) {
        return new Message(CommonConstants.STATUS_ERROR_CODE, msg);
    }

    /**
     * 失败消息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Message error(String code, String msg) {
        return new Message(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
