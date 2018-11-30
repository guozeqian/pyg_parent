package com.pyg.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;


public class QueueConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory=new ActiveMQConnectionFactory
                ("tcp://192.168.128.128:61616");
        Connection connection=factory.createConnection();
        connection.start();
        Session session=connection.createSession(false,Session
                .AUTO_ACKNOWLEDGE);
        Queue queue=session.createQueue("test-queue");
        MessageConsumer consumer=session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage=(TextMessage)message;
                try {
                    System.out.println("接受到消息："+textMessage.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });











    }
}
