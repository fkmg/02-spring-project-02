package com.sxt.rabbitmq.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sxt.rabbitmq.bean.SMS;
import com.sxt.rabbitmq.utils.RabbitConstant;
import com.sxt.rabbitmq.utils.RabbitUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class OrderSystem {

    private static final Logger logger = Logger.getLogger(OrderSystem.class);

    public static void main( String[] args ) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitUtils.getConnection();
            channel = connection.createChannel();
            channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
            //发送消息
            for(int i = 1 ; i <= 100 ; i++) {
                SMS sms = new SMS("乘客" + i, "13900000" + i, "您的车票已预订成功");
                String jsonSMS = new Gson().toJson(sms);
                channel.basicPublish("" , RabbitConstant.QUEUE_SMS , null , jsonSMS.getBytes());
            }
            logger.info("发送成功!");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
