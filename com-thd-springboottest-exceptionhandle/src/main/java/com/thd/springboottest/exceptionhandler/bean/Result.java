package com.thd.springboottest.exceptionhandler.bean;

/**
 * com.thd.springboottest.exceptionhandler.bean.Result
 * User: devil13th
 * Date: 2019/11/24
 * Time: 0:56
 * Description: No Description
 */
public class Result<T>{
    private Integer code; // 状态码

    private String message; // 状态描述信息

    private T data; // 定义为范型

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功方法
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMessage("SUCCESS");
        if (object != null) {
            result.setData(object);
        }

        return result;
    }

    /**
     * 成功但是不返回值
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 失败方法
     * @param code
     * @param message
     * @return
     */
    public static Result error(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);

        return result;
    }
}
