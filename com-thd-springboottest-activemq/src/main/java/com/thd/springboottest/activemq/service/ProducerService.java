package com.thd.springboottest.activemq.service;


import javax.jms.Destination;

/**
 * @author devil13th
 **/
public interface ProducerService {
    /**
     * 功能描述：指定消息队列，还有消息
     * @param destination
     * @param message
     */
    public void sendMessage(Destination destination, final String message);
}
