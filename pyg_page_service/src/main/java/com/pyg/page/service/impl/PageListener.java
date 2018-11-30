package com.pyg.page.service.impl;

import com.pyg.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


@Component
public class PageListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;


    @Override
    public void onMessage(Message message) {
        try{
            System.out.println("PageListener接收消息开始");
            ObjectMessage objectMessage=(ObjectMessage)message;
            Long[] ids=(Long[])objectMessage.getObject();
            System.out.println(ids);
            for(Long id:ids){
                System.out.println(id);
                itemPageService.getItemPageHtml(id);
            }
            System.out.println("PageListener接收消息结束");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
