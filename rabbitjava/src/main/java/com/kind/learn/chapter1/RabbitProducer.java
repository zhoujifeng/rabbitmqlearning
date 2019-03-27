package com.kind.learn.chapter1;

import com.rabbitmq.client.*;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhoujifeng
 * @date 2019/3/27 19:51
 */
public class RabbitProducer {

    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "47.104.224.95";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //建立一个连接
        Connection connection = factory.newConnection();
        //建立一个信道
        Channel channel = connection.createChannel();
        //创建一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,null);
        //创建一个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //队列通过路由键绑定到交换器
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "hello world";
        //发送一条数据
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

        //关闭资源
        channel.close();
        connection.close();
    }
}
