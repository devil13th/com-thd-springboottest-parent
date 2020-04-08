package com.thd.springboottest.requestparameter.serializer.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class FastjsonTimestampSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {

        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info(" fastjson 时间戳序列化:" + object);
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }


        Timestamp ts = (Timestamp)object;
        out.writeLong(ts.getTime());

    }
}
