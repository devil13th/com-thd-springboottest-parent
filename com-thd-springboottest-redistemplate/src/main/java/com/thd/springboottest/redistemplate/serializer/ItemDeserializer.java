package com.thd.springboottest.redistemplate.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.thd.springboottest.redistemplate.bean.Item;

import java.io.IOException;

/**
 * com.thd.jackson.tools.ItemSerializer
 *
 * @author: wanglei62
 * @DATE: 2021/4/22 11:54
 **/
public class ItemDeserializer extends JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String str = jsonParser.getText();
        Item item = new Item();
        item.setId(Integer.parseInt(str));
        item.setName("name");
        item.setType("something");
        return item;
    }
}
