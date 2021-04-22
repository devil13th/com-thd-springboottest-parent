package com.thd.springboottest.redistemplate.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thd.springboottest.redistemplate.bean.Item;

import java.io.IOException;

/**
 * com.thd.jackson.tools.ItemSerializer
 *
 * @author: wanglei62
 * @DATE: 2021/4/22 11:54
 **/
public class ItemSerializer extends JsonSerializer<Item> {
    @Override
    public void serialize(Item value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String str = String.valueOf(value.getId());
        jgen.writeString(str);
    }
}
