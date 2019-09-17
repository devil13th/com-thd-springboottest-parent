package com.thd.springboottest.activemq.service;

/**
 * @author devil13th
 **/
public interface Consumer {
    /**
     * 消费队列消息
     * @param text
     */

    public void onQueueMessage(String text);
    /**
     * 消费主题消息
     * @param text
     */

    public void onTopicMessage(String text);
    public void onTopicMessage2(String text);
}
