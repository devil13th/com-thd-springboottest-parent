package com.thd.springboottest.requestparameter.serializer.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FastjsonTimestampDeserializer implements ObjectDeserializer {

    @Override
    public Date deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info(" fastjson 时间戳反序列化:" + defaultJSONParser.getLexer().stringVal());


        String timestampStr = defaultJSONParser.getLexer().stringVal();
        if(timestampStr != null && timestampStr.trim().length() > 0){
            Timestamp ts =  new Timestamp(Long.valueOf(timestampStr));
            return ts;
        }else{
            return null;
        }


    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
