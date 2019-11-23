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
