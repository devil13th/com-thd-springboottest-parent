package com.thd.springboottest.redistemplate.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//时间戳反序列化时间
public class JsonLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return der(jsonParser,deserializationContext,null);
    }

    @Override
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return der(jsonParser,ctxt,typeDeserializer);
    }

    private LocalDateTime der(JsonParser jsonParser, DeserializationContext context, TypeDeserializer typeDeserializer) throws IOException {
        Long timestamp = jsonParser.getLongValue();
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
    }
}