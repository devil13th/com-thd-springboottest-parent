package com.thd.springboottest.webmvcconfigurer.formatter;

import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

public class BooleanFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport
        implements AnnotationFormatterFactory<BooleanFormat> {


    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(){{
            add(String.class);
            add(Boolean.class);
        }};

    }

    @Override
    public Printer<?> getPrinter(BooleanFormat booleanFormat, Class<?> aClass) {
        BooleanFormatter booleanFormatter = new BooleanFormatter();
        booleanFormatter.setTrueTag(booleanFormat.trueTag());
        return booleanFormatter;
    }

    @Override
    public Parser<?> getParser(BooleanFormat booleanFormat, Class<?> aClass) {
        BooleanFormatter booleanFormatter = new BooleanFormatter();
        booleanFormatter.setTrueTag(booleanFormat.trueTag());
        return booleanFormatter;
    }
}
