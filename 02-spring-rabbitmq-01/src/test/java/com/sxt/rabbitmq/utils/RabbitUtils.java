package com.sxt.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



public class RabbitUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    static {
        connectionFactory.setHost("192.168.1.133");
        connectionFactory.setPort(5672);//5672是RabbitMQ的默认端口号
        connectionFactory.setUsername("fkmg");
        connectionFactory.setPassword("fkmg");
        connectionFactory.setVirtualHost("/myFirstMq");
    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
