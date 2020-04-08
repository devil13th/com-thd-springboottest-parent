package com.thd.springboottest.requestparameter.serializer.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FastjsonDateDeserializer implements ObjectDeserializer {

    @Override
    public Date deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info(" fastjson 日期反序列化:" + defaultJSONParser.getLexer().stringVal());

        String dateStr = defaultJSONParser.getLexer().stringVal();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;

    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
