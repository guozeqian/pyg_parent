package com.pyg.task;


import com.pyg.mapper.TbSeckillGoodsMapper;
import com.pyg.pojo.TbSeckillGoods;
import com.pyg.pojo.TbSeckillGoodsExample;
import com.pyg.pojo.TbSeckillOrder;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    /**
     * 刷新秒杀商品
     * jiane
     * 2018/9/27 14:48
     */
    @Scheduled(cron = "0 * * * * ?")
    public void refreshSeckillGoods() {
        System.out.println("执行任务调度" + new Date().toLocaleString());
        List ids = new ArrayList(
                redisTemplate.boundHashOps("seckillGoods").keys()
        );
        TbSeckillGoodsExample example = new TbSeckillGoodsExample();
        TbSeckillGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        criteria.andStockCountGreaterThan(0);
        criteria.andStartTimeLessThan(new Date());
        criteria.andEndTimeGreaterThan(new Date());
        criteria.andIdNotIn(ids);
        List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper
                .selectByExample(example);
        for (TbSeckillGoods seckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId
                    (), seckillGoods);
        }
        System.out.println("将" + seckillGoodsList.size() + "条商品装入缓存");

    }

    /**
     * 秒殺商品移除
     * jiane
     * 2018/9/27 15:44
     */
    @Scheduled(cron = "* * * * * ?")
    public void removeSeckillGoods() {
        System.out.println("移除秒杀商品任务执行" + new Date().toLocaleString());
        List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps
                ("seckillGoods").values();
        for (TbSeckillGoods seckill : seckillGoodsList) {
            if (seckill.getEndTime().getTime() < new Date().getTime()) {
                seckillGoodsMapper.updateByPrimaryKey(seckill);
                redisTemplate.boundHashOps("seckillGoods").delete(seckill
                        .getId());
                System.out.println("移除秒杀商品" + seckill.getId());
            }
        }
        System.out.println("移除秒杀商品任务结束");

    }


}
