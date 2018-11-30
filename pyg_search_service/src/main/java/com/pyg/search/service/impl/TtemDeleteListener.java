package com.pyg.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.Arrays;
import java.util.List;

@Component
public class TtemDeleteListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;


    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("TtemDeleteListener监听收到的消息");

            ObjectMessage objectMessage=(ObjectMessage)message;
            Long[] ids=(Long[])objectMessage.getObject();
            System.out.println("商品删除后监听者监听到的消息:" + ids);

            //调用业务方法执行solr索引库的删除
            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));

            System.out.println("TtemDeleteListener删除索引库结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
