package com.pyg.sellergoods.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.pojogroup.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.sellergoods.service.GoodsService;

import entity.PageResult;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbBrandMapper brandMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbSellerMapper sellerMapper;

    /*
* 根据id和status查询结果
* */
    public List<TbItem> findByIdsAndStatus(Long[] ids,String status){
        TbItemExample example=new TbItemExample();
        com.pyg.pojo.TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdIn(Arrays.asList(ids));
        criteria.andStatusEqualTo(status);
        return itemMapper.selectByExample(example);
    }


    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods goods) {
        goods.getGoods().setAuditStatus("0");
        goodsMapper.insert(goods.getGoods());
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insert(goods.getGoodsDesc());
        for (TbItem item : goods.getItemList()) {
            String title = goods.getGoods().getGoodsName();
            Map<String, Object> specMap = JSON.parseObject(item.getSpec());
            for (String key : specMap.keySet()) {
                title += " " + specMap.get(key);
            }
            item.setTitle(title);
            item.setGoodsId(goods.getGoods().getId());//商品SPU编号
            item.setSellerId(goods.getGoods().getSellerId());//商家编号
            item.setCategoryid(goods.getGoods().getCategory3Id());//商品分类编号（3级）
            item.setCreateTime(new Date());//创建日期
            item.setUpdateTime(new Date());//修改日期
            //品牌名称
            TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods()
                    .getBrandId());
            item.setBrand(brand.getName());
            //分类名称
            TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods
                    .getGoods().getCategory3Id());
            item.setCategory(itemCat.getName());
            //商家名称
            TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods
                    ().getSellerId());
            item.setSeller(seller.getNickName());
            //图片地址（取spu的第一个图片）
            List<Map> imageList = JSON.parseArray(goods.getGoodsDesc()
                    .getItemImages(), Map.class);
            if (imageList.size() > 0) {
                item.setImage((String) imageList.get(0).get("url"));
            }
            itemMapper.insert(item);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Goods goods) {
        goodsMapper.updateByPrimaryKey(goods.getGoods());
        goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getGoods().getId());
        itemMapper.deleteByExample(example);

        insertIntoTbItem(goods);
    }

    private void insertIntoTbItem(Goods goods) {
        if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            for (TbItem item : goods.getItemList()) {
                String title = goods.getGoods().getGoodsName();
                Map<String, String> map = JSON.parseObject(item.getSpec(),
                        Map.class);
                for (String key : map.keySet()) {
                    title += " " + map.get(key);
                }
                item.setTitle(title);
                setItem(goods, item);
                itemMapper.insert(item);
            }
        } else {
            TbItem item = new TbItem();
            item.setPrice(goods.getGoods().getPrice());
            item.setNum(9999);
            item.setStatus("1");
            item.setIsDefault("1");
            item.setSpec("{}");
            item.setTitle(goods.getGoods().getGoodsName());
            setItem(goods, item);
            itemMapper.insert(item);
        }
    }

    private void setItem(Goods goods, TbItem item) {
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc()
                .getItemImages(), Map.class);
        if (imageList != null && imageList.size() > 0) {
            item.setImage((String) imageList.get(0).get("url"));
        }
        item.setCategoryid(goods.getGoods().getCategory3Id());

        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        item.setGoodsId(goods.getGoods().getId());
        item.setSeller(goods.getGoods().getSellerId());

        //设置品牌冗余字段
        item.setBrand(brandMapper.selectByPrimaryKey(goods.getGoods()
                .getBrandId()).getName());
        //设置分类冗余字段
        item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getGoods()
                .getCategory3Id()).getName());
        //设置商家冗余字段
        item.setSeller(sellerMapper.selectByPrimaryKey(goods.getGoods()
                .getSellerId()).getNickName());


    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        Goods goods = new Goods();
        goods.setGoods(goodsMapper.selectByPrimaryKey(id));
        goods.setGoodsDesc(goodsDescMapper.selectByPrimaryKey(id));
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);//外键条件
        List<TbItem> itemList = itemMapper.selectByExample(example);
        goods.setItemList(itemList);
        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() >
                    0) {
                criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length()
                    > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus()
                    .length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable()
                    .length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() +
                        "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() >
                    0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec()
                    .length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() +
                        "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() >
                    0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample
                (example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods goods=goodsMapper.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(goods);
        }
    }

    @Override
    public void updateMarketable(Long[] ids, String isMarketable) {
        for (Long id : ids) {
            TbGoods goods=goodsMapper.selectByPrimaryKey(id);
            goods.setIsMarketable(isMarketable);
            goodsMapper.updateByPrimaryKey(goods);
        }
    }

}
