package com.sxt.rabbitmq.workqueue;

import com.rabbitmq.client.*;
import com.sxt.rabbitmq.utils.RabbitConstant;
import com.sxt.rabbitmq.utils.RabbitUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author 白起老师
 * 消费者
 */
public class SMSSender2 {

    private static Logger logger = Logger.getLogger(SMSSender2.class);

    //计数器
    private static Integer count = 0;

    public static void main(String[] args) throws IOException {


        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);

        //如果不写basicQos（1），则自动MQ会将所有请求平均发送给所有消费者
        //basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的
        channel.basicQos(1);//处理完一个取一个

        /*channel.basicConsume(RabbitConstant.QUEUE_SMS , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                System.out.println("SMSSender2-短信发送成功:" + jsonSMS);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });*/

        DeliverCallback deliverCallback = (consumerTag,message)->{
            logger.info("确认消息:"+consumerTag);
            System.out.println("确认消息:"+consumerTag);
            String jsonSMS = new String(message.getBody());
            System.out.println("SMSSender2-短信发送成功:" + jsonSMS);
            if (count < 10){
                //确认消息
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
            if (count >9 && count<12){
                logger.info("拒绝签收:"+count+"条消息");
                channel.basicNack(message.getEnvelope().getDeliveryTag(),false,false);
            }
            if (count > 12){
                channel.basicAck(message.getEnvelope().getDeliveryTag(),true);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        };

        CancelCallback cancelCallback = consumerTag ->{
            logger.info("被取消的消息:"+consumerTag);
            System.out.println("被取消的消息:"+consumerTag);
        };
        ConsumerShutdownSignalCallback consumerShutdownSignalCallback = (consumerTag,sig)->{
            logger.info("发生异常的消息:"+consumerTag);
            System.out.println("异常消息:"+sig.getMessage());
        };
        channel.basicConsume(RabbitConstant.QUEUE_SMS ,
                false,deliverCallback,cancelCallback,
                consumerShutdownSignalCallback);
    }
}
