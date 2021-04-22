package com.thd.springboottest.redistemplate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

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
        ser(timestamp, jsonGenerator, serializerProvider, null);
    }

    @Override
    public void serializeWithType(Timestamp value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        ser(value, gen, serializers, typeSer);
    }

    private void ser(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy||MM||dd HH:mm:ss");
        String createTimeStr = sdf.format(new Date(timestamp.getTime()));
        jsonGenerator.writeString(createTimeStr);
    }
}
