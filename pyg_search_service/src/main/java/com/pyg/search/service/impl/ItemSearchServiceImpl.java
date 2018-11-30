package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.ctc.wstx.util.StringUtil;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Map<String, Object> searchMap(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //1.查询列表
        map.putAll(searchList(searchMap));

        //分组

        List categoryList = searchCategoryList(searchMap);
        if (categoryList.size() > 0) {
            map.put("categoryList", categoryList);
        }
        String category = (String) searchMap.get("category");
        if (StringUtils.isNotEmpty(category)) {
            map.putAll(searchBrandAndSpecList(category));
        } else {
            if (categoryList.size() > 0) {
                map.putAll(searchBrandAndSpecList((String) categoryList.get
                        (0)));
            }
        }

        return map;
    }


    /**
     * 查询品牌和规格列表
     *
     * @param category 分类名称
     * @return
     */
    private Map searchBrandAndSpecList(String category) {
        Map map = new HashMap();
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get
                (category);

        if (typeId != null) {
            List brandList = (List) redisTemplate.boundHashOps("brandList").get
                    (typeId);
            map.put("brandList", brandList);

            List specList = (List) redisTemplate.boundHashOps("specList").get
                    (typeId);
            map.put("specList", specList);
        }

        return map;
    }


    /**
     * 根据关键字分组查询
     *
     * @param searchMap
     * @return
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList();
        Query query = new SimpleQuery();
        //按照关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get
                ("key_words"));
        query.addCriteria(criteria);
        GroupOptions groupOptions = new GroupOptions().addGroupByField
                ("item_category");
        query.setGroupOptions(groupOptions);
        //得到分组
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem
                .class);
        //根据列得到分组结果集
        GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
        //得到分组结果入口页
        Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
        List<GroupEntry<TbItem>> content = groupEntries.getContent();

        for (GroupEntry<TbItem> entry : content) {
            list.add(entry.getGroupValue());
        }

        return list;
    }


    /**
     * 根据关键字搜索列表
     *
     * @param searchMap
     * @return
     */
    private Map searchList(Map searchMap) {
        Map map = new HashMap();
        HighlightQuery query = new SimpleHighlightQuery();


        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        String keywords = searchMap.get("key_words").toString().replace(" ",
                "");
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        query.addCriteria(criteria);

        if (searchMap.get("cagetory") != null && !"".equals(searchMap.get
                ("cagetory"))) {
            Criteria filterCriteria = new Criteria("item_cagetory").is
                    (searchMap.get("cagetory"));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        if (searchMap.get("brand") != null && !"".equals(searchMap.get
                ("brand"))) {
            Criteria filterCriteria = new Criteria("item_brand").is
                    (searchMap.get("brand"));
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        if (searchMap.get("spec") != null) {
            Map<String, String> specMap = (Map) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                Criteria filterCriteria = new Criteria("item_spec_" + key).is
                        (specMap.get(key));
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //价格区间参数
        if (!"".equals(searchMap.get("price"))) {
            String[] price = searchMap.get("price").toString().split("-");
            if (!price[0].equals("0")) {
                Criteria filterCriteria = new Criteria("item_price")
                        .greaterThanEqual(price[0]);
                FilterQuery filterQuery = new SimpleFacetQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if (!"*".equals(price[1])) {
                Criteria filterCriteria = new Criteria("item_price")
                        .lessThanEqual(price[1]);
                FilterQuery filterQuery = new SimpleFacetQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }


        //排序
        String sort = searchMap.get("sort").toString();
        String sortField = searchMap.get("sortField").toString();
        if (sort != null && !"".equals(sort) && sortField != null && !""
                .equals(sortField)) {
            if (sort.equals("ASC")) {
                Sort sort1 = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort1);
            }
            if (sort.equals("DESC")) {
                Sort sort1 = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort1);
            }
        }


        //分页查询
        Integer pageNo = Integer.parseInt(searchMap.get("pageNo").toString());
        if (pageNo == null) {
            pageNo = 1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize == null) {
            pageSize = 20;
        }
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);


        //高亮
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage
                (query, TbItem.class);

        for (HighlightEntry<TbItem> h : page.getHighlighted()) {//循环高亮入口集合
            TbItem item = h.getEntity();//获取原实体类
            if (h.getHighlights().size() > 0 && h.getHighlights().get(0)
                    .getSnipplets().size() > 0) {
                item.setTitle(h.getHighlights().get(0).getSnipplets().get(0))
                ;//设置高亮的结果
            }
        }
        map.put("rows", page.getContent());
        map.put("totalPages", page.getTotalPages());
        map.put("total", page.getTotalElements());
        return map;
    }

    @Override
    public void importGoodsList(List goodsList) {
        solrTemplate.saveBeans(goodsList);
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        System.out.println("删除商品ID"+goodsIdList);
        Query query=new SimpleQuery();
        Criteria criteria=new Criteria("item_goodsid").in(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
