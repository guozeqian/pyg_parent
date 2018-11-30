package com.pyg.manage.controller;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
//import com.pyg.page.service.ItemPageService;
import com.pyg.pojo.TbItem;
import com.pyg.pojogroup.Goods;
//import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbGoods;
import com.pyg.sellergoods.service.GoodsService;

import entity.PageResult;
import entity.Result;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
//    @Reference
//    private ItemSearchService itemSearchService;

//    @Reference(timeout = 40000)
//    private ItemPageService itemPageService;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queuesolrtDestination;
    @Autowired
    private Destination queuedeleteDestination;
    @Autowired
    private Destination topicPageDestination;
    @Autowired
    private Destination topicPageDeleteDestination;

    //发送文本消息
    private void sentTextMessage(final String text) {
        jmsTemplate.send(queuesolrtDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(text);
            }
        });
    }

    private void sendSolrDeleteMessage(final Long[] ids) {
        jmsTemplate.send(queuedeleteDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(ids);
            }
        });
    }


    private void sentPageMessage(final Long[] id) {
        jmsTemplate.send(topicPageDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(id);
            }
        });
    }

    private void sendPageDeleteMessage(final Long[] ids) {
        jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(ids);
            }
        });
    }

    @RequestMapping("/getHtml")
    public Result getHtml(Long goodsId) {
        try {
//            itemPageService.getItemPageHtml(goodsId);
            Long[] ids = {goodsId};
            sentPageMessage(ids);


            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
    }


    //修改审批
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            goodsService.updateStatus(ids, status);
            //按照SPU ID查询 SKU列表(状态为1)
            if (status.equals("1")) {//审核通过
                List<TbItem> itemList = goodsService.findByIdsAndStatus(ids,
                        status);
                System.out.println(ids);
                System.out.println(ids);
                //调用搜索接口实现数据批量导入
                if (itemList.size() > 0) {
//                    itemSearchService.importGoodsList(itemList);
//                    activemq方式
                    final String jsonString = JSON.toJSONString
                            (itemList);
                    sentTextMessage(jsonString);

                    System.out.println("导入数据成功");
                } else {
                    System.out.println("没有明细数据");
                }
                System.out.println("生成静态页面开始");
//                for (Long id : ids) {
////                    itemPageService.getItemPageHtml(id);
//
//                    System.out.println(id);
//                }

                sentPageMessage(ids);
                System.out.println("生成静态页面结束");
            }
            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }

    }

    //下架
    @RequestMapping("/updateMarketable")
    public Result updateMarketable(Long[] ids, String isMarketable) {
        try {
            goodsService.updateMarketable(ids, isMarketable);
            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Goods goods) {
        try {
            goodsService.update(goods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);

            //itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            sendSolrDeleteMessage(ids);
            sendPageDeleteMessage(ids);

            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int rows) {
        return goodsService.findPage(goods, page, rows);
    }

}
