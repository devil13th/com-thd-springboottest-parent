package com.thd.springboottest.requestparameter.serializer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳序列化器
 *
 * @author: wanglei62
 * @DATE: 2020/4/1 11:11
 **/
public class JsonTimestampSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        logger.info(" jackson 日期序列化:" + timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy||MM||dd HH:mm:ss");
        String createTimeStr = sdf.format(new Date(timestamp.getTime()));
        jsonGenerator.writeString(createTimeStr);
    }
}
