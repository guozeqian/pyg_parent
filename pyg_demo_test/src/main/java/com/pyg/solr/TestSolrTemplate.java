package com.pyg.solr;


import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class TestSolrTemplate {
    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void demoAdd(){
        TbItem item=new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setCategoryid(1L);
        item.setGoodsId(2L);
        item.setTitle("华为mate9");
        item.setSeller("华为专卖店");
        item.setPrice(new BigDecimal(9999));

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }
    //根据主键查询
    @Test
    public void demoFindOne(){
        TbItem item =solrTemplate.getById(1,TbItem.class);
        System.out.println(item);
    }

    //根据主键删除
    @Test
    public void demoDeleteOne(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }
    /**
     * 准备分页数据的
     */
    @Test
    public void testAddList(){

        List<TbItem> list=new ArrayList();
        for(int i=1;i<=100;i++){
            TbItem item=new TbItem();
            item.setId(i+0L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为2号专卖店");
            item.setTitle("华为Mate"+i);
            item.setPrice(new BigDecimal(2000+i));
            Map map= JSON.parseObject("{'机身内存':'16G','网络':'3G'}",Map.class);
            item.setSpecMap(map);
            list.add(item);
        }

        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Test
    public void testPageQuery(){
        Query query = new SimpleQuery("*:*");
        query.setOffset((3 - 1) * 10);//起始偏移量  (n-1)*size
        query.setRows(10);//每页数据的size
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);

        int totalPages = tbItems.getTotalPages();
        System.out.println("总页数" + totalPages);
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数" + totalElements);
        List<TbItem> tbItemList = tbItems.getContent();
        for (TbItem tbItem : tbItemList) {
            System.out.println("商品标题:" + tbItem.getTitle());
        }
    }

    /**
     * 多条件查询
     */
    @Test
    public void testPageQueryMutil(){

        Query query = new SimpleQuery("*:*");
//        query.setOffset((3 - 1) * 10);//起始偏移量  (n-1)*size
//        query.setRows(10);//每页数据的size

        Criteria criteria = new Criteria("item_keywords").contains("3");
        criteria=criteria.and("item_title").contains("5");
        query = query.addCriteria(criteria);//添加查询条件
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);

        int totalPages = tbItems.getTotalPages();
        System.out.println("总页数" + totalPages);
        long totalElements = tbItems.getTotalElements();
        System.out.println("总记录数" + totalElements);
        List<TbItem> tbItemList = tbItems.getContent();
        for (TbItem tbItem : tbItemList) {
            System.out.println("商品标题:" + tbItem.getTitle());
        }
    }
    @Test
    public void deleteAll(){

        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
