package com.pyg.springactivemq;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage)message;
        try {
            System.out.println("接收到消息"+textMessage.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
