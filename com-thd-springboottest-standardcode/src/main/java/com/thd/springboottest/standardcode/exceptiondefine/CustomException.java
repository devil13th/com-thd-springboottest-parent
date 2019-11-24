package com.thd.springboottest.standardcode.exceptiondefine;


import com.thd.springboottest.standardcode.bean.MessageEnum;

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
