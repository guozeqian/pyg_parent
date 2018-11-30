package com.pyg.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

@Component
public class ItemSearchListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;


    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("ItemSearchListener监听收到的消息");
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
            List<TbItem> itemList = JSON.parseArray(text, TbItem.class);
            itemSearchService.importGoodsList(itemList);
            System.out.println("ItemSearchListener消息执行完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
