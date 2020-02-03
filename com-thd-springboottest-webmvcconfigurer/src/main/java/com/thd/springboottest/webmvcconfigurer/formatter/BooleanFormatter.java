package com.thd.springboottest.webmvcconfigurer.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

public class BooleanFormatter implements Formatter<Boolean> {

    private String[] trueTag;


    @Override
    public Boolean parse(String s, Locale locale) throws ParseException {
        if (trueTag != null && trueTag.length > 0) {
            return Arrays.asList(trueTag).contains(s);
        } else {
            switch (s.toLowerCase()) {
                case "true":
                case "1":
                case "是":
                case "有":
                    return true;
            }
        }
        return false;
    }

    @Override
    public String print(Boolean aBoolean, Locale locale) {
        return aBoolean ? "true" : "false";
    }

    public String[] getTrueTag() {
        return trueTag;
    }

    public void setTrueTag(String[] trueTag) {
        this.trueTag = trueTag;
    }
}