package com.thd.springboottest.config.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.config.config.Cfg
 *
 * @author: wanglei62
 * @DATE: 2020/1/1 16:34
 **/
@Component
@ConfigurationProperties(value="cfg")
public class Cfg {
    private CfgBean cfgBean;
    private String process;
    private String appName;
    private String filePath;

    @Override
    public String toString() {
        return "Cfg{" +
                "cfgBean=" + cfgBean.toString() +
                ", process='" + process + '\'' +
                ", appName='" + appName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public CfgBean getCfgBean() {
        return cfgBean;
    }

    public void setCfgBean(CfgBean cfgBean) {
        this.cfgBean = cfgBean;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
