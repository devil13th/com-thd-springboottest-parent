package com.thd.springboottest.webmvcconfigurer.formatter;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface BooleanFormat {
    String[] trueTag() default {};
}
