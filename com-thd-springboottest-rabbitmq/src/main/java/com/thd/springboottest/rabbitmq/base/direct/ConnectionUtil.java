package com.thd.springboottest.rabbitmq.base.direct;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * com.thd.springboottest.rabbitmq.base.direct.ConnectionUtil
 *
 * @author: wanglei62
 * @DATE: 2020/2/18 13:57
 **/
public class ConnectionUtil {
    private static final String RABBIT_HOST = "127.0.0.1";
    private static final String RABBIT_USERNAME  = "guest";
    private static final String RABBIT_PASSWORD = "guest";
    private static final String RABBIT_VIRTUALHOST = "";
    private static Connection connection = null;

    public static Connection getConnection (){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBIT_HOST);
        // connectionFactory.setVirtualHost(RABBIT_VIRTUALHOST);
        connectionFactory.setUsername(RABBIT_USERNAME);
        connectionFactory.setPassword(RABBIT_PASSWORD);
        connectionFactory.setPort(5672);
        try{
            connection = connectionFactory.newConnection();
        }catch(IOException e){
            e.printStackTrace();
        }catch(TimeoutException e){
            e.printStackTrace();
        }
        return connection;
    }
}
