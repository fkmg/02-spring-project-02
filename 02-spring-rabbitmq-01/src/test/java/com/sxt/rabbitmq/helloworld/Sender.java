package com.sxt.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "hello";

    public static void main( String[] args ){
        ConnectionFactory factory = new ConnectionFactory();
        Channel channel = null;
        Connection connection = null;
        try {
            factory.setHost("192.168.1.133");
            factory.setVirtualHost("/myFirstMq");
            factory.setUsername("fkmg");
            factory.setPassword("fkmg");
            factory.setPort(5672);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = "Hello 师姐!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            try {
                if (channel != null){
                    channel.close();
                }

                if (connection != null){
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

}
