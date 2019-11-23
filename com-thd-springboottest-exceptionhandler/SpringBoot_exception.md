[TOC]

# SpringBoot 全局异常处理

# 统一返回数据结构

## 定义接口返回数据结构

先定义接口返回数据结构，code为0表示操作成功，非0表示异常。其中data只有在处理成功才会返回，其他情况不会返回，或者那些不需要返回数据的接口（更新、删除…）

```
{
 	"code": 0,
 	"message": "SUCCESS",
 	"data": {

 	}
}
```
## 数据接口字段模型定义

创建`Result.java`类，对以上数据接口涉及的字段进行定义。

`Result.java`

```
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

   
}

```

## 封装接口返回方法

在上面Result类基础上，对返回的成功、失败进行统一封装。（这种做法是参考ResponseEntity的做法）

`Result.java`

```
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

```
# 统一异常处理

## 状态消息枚举

项目用到的状态码、描述信息要有个文件统一去做枚举定义，一方面可以实现复用，另一方面如果状态码、描述有改动只需要在定义枚举的地方改动即可。

新建`MessageEnum.java`枚举

```
package com.thd.springboottest.exceptionhandler.bean;

/**
 * com.thd.springboottest.exceptionhandler.bean.ResultEnum
 * User: devil13th
 * Date: 2019/11/24
 * Time: 0:59
 * Description: No Description
 */
public enum MessageEnum {
    NULL_ERROR(1000,"空指针"),
    SYSTEM_ERROR(1001, "系统异常"),
    NAME_EXCEEDED_CHARRACTER_LIMIT(2000, "姓名超过了限制，最大4个字符!");

    private Integer code;
    private String message;

    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

```

## 自定义异常类

> Spring Boot框架只对抛出的RuntimeException异常进行事物回滚，那么Spring Boot封装的RuntimeException异常也是继承的Exception

新建`MyParentException.java`类，继承于`RuntimeException` ，这是一个基类，所有自定义的异常都继承自该类，目的是为了统一接口

`MyParentException.java`

```
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

```





## @ControllerAdvice统一处理异常

关于`@ControllerAdvice`更多内容可参考官方文档`https://docs.spring.io/spring-framework/docs/5.0.0.M1/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html`

- `@ControllerAdvice`，spring3.2新增加，用于定义 `@ExceptionHandler`, `@InitBinder`, 和 `@ModelAttribute`方法，并应用到所有的`@RequestMapping`方法。

- `@ExceptionHandler`，拦截异常，方法里的value是指需要拦截的异常类型，通过该注解可实现自定义异常处理。

- ### `注意：` 之前讲过AOP面向切面编程，注解`@AfterThrowing`会捕捉到项目中的错误信息，如果使用了此注解，它捕获到错误信息之后，会直接返回，是不会触发`@ControllerAdvice`注解的。

```
package com.thd.springboottest.exceptionhandler.handler;

import com.thd.springboottest.exceptionhandler.bean.Result;
import com.thd.springboottest.exceptionhandler.exceptionbean.MyParentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.exceptionhandler.handler.ExceptionHandle
 * User: devil13th
 * Date: 2019/11/24
 * Time: 1:01
 * Description: No Description
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        logger.info("进入error");

        // 是否属于自定义异常

        System.out.println(e instanceof MyParentException);
        if (e instanceof MyParentException) {
            MyParentException myException = (MyParentException) e;
            return Result.error(myException.getCode(), myException.getMessage());
        } else {
            logger.error("系统异常 {}", e);

            return Result.error(1000, "系统异常!");
        }
    }
}

```

再定义一个异常类`CustomException.java`

`CustomException.java`

```
package com.thd.springboottest.exceptionhandler.exceptionbean;

import com.thd.springboottest.exceptionhandler.bean.MessageEnum;
import com.thd.springboottest.exceptionhandler.exceptionbean.MyParentException;

/**
 * com.thd.springboottest.exceptionhandler.handler.CustomException
 * User: devil13th
 * Date: 2019/11/24
 * Time: 1:50
 * Description: No Description
 */
public class CustomException extends MyParentException {
    public CustomException(){
        super(MessageEnum.NAME_EXCEEDED_CHARRACTER_LIMIT);
    }
}

```



# 测试

## 创建一个测试的Controller

```
package com.thd.springboottest.exceptionhandler.controller;

import com.thd.springboottest.exceptionhandler.bean.Result;
import com.thd.springboottest.exceptionhandler.exceptionbean.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.exceptionhandler.controller.TestController
 * User: devil13th
 * Date: 2019/11/24
 * Time: 0:56
 * Description: No Description
 */
@Controller
@RequestMapping("/exceptionhandler")
public class TestController {

    @RequestMapping("/test/{s}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/exceptionhandler/test/error
    public Result test(@PathVariable String s){

        if("error".equals(s)){
            throw new CustomException();
        }

        return Result.success(s);
    }

}

```

## 正常的访问

http://127.0.0.1:8899/thd/exceptionhandler/test/xxx

## 出现异常的访问

http://127.0.0.1:8899/thd/exceptionhandler/test/error