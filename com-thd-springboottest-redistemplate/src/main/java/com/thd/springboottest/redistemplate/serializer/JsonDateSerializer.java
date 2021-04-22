package com.thd.springboottest.redistemplate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        ser(value, jgen, provider, null);
    }

    @Override
    public void serializeWithType(Date value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        ser(value, gen, serializers, typeSer);
    }


    private void ser(Date value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {

        //        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(value);
        gen.writeString(formattedDate);


    }

}
