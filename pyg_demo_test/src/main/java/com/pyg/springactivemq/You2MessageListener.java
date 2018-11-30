package com.pyg.springactivemq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class You2MessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text+"1111111111111");
        } catch (Exception e) {
            e.printStackTrace();
        //    org.springframework.jms.listener.DefaultMessageListenerContainer
        }
    }
}
