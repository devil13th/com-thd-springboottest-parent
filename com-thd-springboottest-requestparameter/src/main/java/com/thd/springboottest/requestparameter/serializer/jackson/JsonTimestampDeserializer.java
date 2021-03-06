package com.thd.springboottest.requestparameter.serializer.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 时间戳序列化器
 *
 * @author: wanglei62
 * @DATE: 2020/4/1 11:11
 **/
public class JsonTimestampDeserializer extends JsonDeserializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        String value = jsonParser.getText();
        logger.info(" jackson 日期序列化:" + value);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy&&MM&&dd HH$$mm$$ss");
        try{
            return new Timestamp(sdf.parse(value).getTime());
        }catch(Exception e){
            throw new RuntimeException("Timestamp格式错误");
        }
    }
}
