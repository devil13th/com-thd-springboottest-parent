package com.thd.springboottest.webmvcconfigurer.formatter;

public class BoolFormatterDemoDto {
    @BooleanFormat(trueTag = {"YES","OK","是"})
    private boolean exists = false;

    private String name ;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
