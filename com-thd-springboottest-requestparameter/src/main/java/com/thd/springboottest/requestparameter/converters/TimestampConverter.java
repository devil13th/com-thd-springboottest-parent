package com.thd.springboottest.requestparameter.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;

/**
 * com.thd.springboottest.datetimestamp.converter.DateConverter
 * 字符串转换成日期
 * @author: wanglei62
 * @DATE: 2020/4/3 8:02
 **/
public class TimestampConverter implements Converter<String, Timestamp> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String timestampFormat = "^\\d+$";
    @Override
    public Timestamp convert(String value) {
        logger.info(" 转换时间戳 :" + value);

        if(value == null || value.trim().equals("") || value.equalsIgnoreCase("null")){
            return null;
        }

        value = value.trim();

        try{
            if(value.matches(timestampFormat)){
                Long timestamp = new Long(value);
                return new Timestamp(timestamp);
            }else{
                throw new RuntimeException(String.format(" parser %s to Date Failed",value));
            }
        }catch(Exception e){
            throw new RuntimeException(String.format(" parser %s to Date Failed",value));
        }


    }
}
