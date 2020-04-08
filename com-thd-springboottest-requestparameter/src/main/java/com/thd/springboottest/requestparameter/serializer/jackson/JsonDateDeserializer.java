package com.thd.springboottest.requestparameter.serializer.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String dateStr = jsonParser.getText();
        logger.info(" jackson 日期反序列化:" + dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }
}
