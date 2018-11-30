package com.pyg.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueProducer {
    public static void main(String[] args) throws Exception {
        //1.创建连接工厂
        ConnectionFactory factory=new ActiveMQConnectionFactory
                ("tcp://192.168.128.128:61616");
        //2.获取连接
        Connection connection=factory.createConnection();
        //3.启动连接
        connection.start();
        //4.获取session（参数1：是否启动事务，参数2：消息确实模式）
        Session session=connection.createSession(false,Session
                .AUTO_ACKNOWLEDGE);
        //5.创建队列对象
        Queue queue=session.createQueue("test-queue");
        //6.创建消息成缠着
        MessageProducer producer=session.createProducer(queue);
        //7.创建消息
        TextMessage textMessage=session.createTextMessage("欢迎来到神奇的blueshit");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();










    }
}
