package com.thd.springboottest.datetimestamp.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.converter.DateConverter
 * 字符串转换成日期
 * @author: wanglei62
 * @DATE: 2020/4/3 8:02
 **/
public class DateConverter implements Converter<String, Date> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String dateFormat = "yyyy_MM_dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy_MM_dd";
    private static final String timestampFormat = "^\\d+$";
    @Override
    public Date convert(String value) {
        logger.info(" 转换日期 :" + value);

        if(value == null || value.trim().equals("") || value.equalsIgnoreCase("null")){
            return null;
        }

        value = value.trim();

        try{
            if(value.contains("_")){
                SimpleDateFormat sdf;

                if(value.contains(":")){ // 长日期 yyyy_MM_dd HH:mm:ss
                    sdf = new SimpleDateFormat(dateFormat);
                }else{   // 短日期 yyyy_MM_dd
                    sdf = new SimpleDateFormat(shortDateFormat);
                }
                return sdf.parse(value);
            }else if(value.matches(timestampFormat)){ // 时间戳 1585875084
                Long timestamp = new Long(value);
                return new Date(timestamp);
            }
        }catch(Exception e){
            throw new RuntimeException(String.format(" parser %s to Date Failed",value));
        }

        throw new RuntimeException(String.format(" parser %s to Date Failed",value));
    }
}
