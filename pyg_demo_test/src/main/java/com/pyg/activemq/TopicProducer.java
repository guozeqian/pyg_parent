package com.pyg.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    public static void main(String[] args) throws JMSException {

        ConnectionFactory factory=new ActiveMQConnectionFactory
                ("tcp://192.168.128.128:61616");
        Connection connection=factory.createConnection();
        connection.start();
        Session session=connection.createSession(false,Session
                .AUTO_ACKNOWLEDGE);
        Topic topic=session.createTopic("test-topic");
        MessageProducer producer=session.createProducer(topic);
        TextMessage textMessage=session.createTextMessage("欢迎来到blueshit的世界");

        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();




    }


}
