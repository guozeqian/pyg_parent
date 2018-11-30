package com.pyg.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pay.service.WeixinPayService;
import com.pyg.pojo.TbPayLog;
import com.pyg.pojo.TbSeckillOrder;
import com.pyg.seckill.service.SeckillOrderService;
import com.pyg.utils.IdWorker;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference
    private WeixinPayService weixinPayService;
    @Reference
    private SeckillOrderService seckillOrderService;

    @RequestMapping("getNative")
    public Map getNative() {
        String userId = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        TbSeckillOrder seckillOrder = seckillOrderService
                .searchOrderFromRedisByUserId(userId);
        if (seckillOrder != null) {
            Long fen = (long) (seckillOrder.getMoney().doubleValue() * 100);
            return weixinPayService.createNative(seckillOrder.getId() + "",
                    fen + "");
        } else {
            return new HashMap();
        }
    }

    @RequestMapping("queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        Result result = new Result(false, "支付失败");
        String userId = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        int n = 0;
        synchronized (out_trade_no.intern()) {
            while (true) {
                System.out.println(n);
                try {
                    Map<String, String> map = weixinPayService.queryPayStatus
                            (out_trade_no);
                    if (map == null) {
                        result = new Result(false, "支付失败");
                        break;
                    }
                    if (map.get("trade_state").equals("SUCCESS")) {
                        result = new Result(true, "支付成功");
                        seckillOrderService.saveOrderFromRedisToDb(userId,
                                Long.valueOf(out_trade_no), map.get
                                        ("transaction_id"));
                        break;
                    }

                    Thread.sleep(3000);//间隔三秒

                    n++;
                    if (n > 20) {
                        result = new Result(false, "支付超时");
                        //1.调用微信的关闭订单接口（学员实现）
                        Map<String, String> payresult = weixinPayService
                                .closePay(out_trade_no);
                        if (!"SUCCESS".equals(payresult.get("result_code")))
                        {//如果返回结果是正常关闭
                            if ("ORDERPAID".equals(payresult.get("err_code"))) {
                                result = new Result(true, "支付成功");
                                seckillOrderService.saveOrderFromRedisToDb
                                        (userId, Long.valueOf(out_trade_no),
                                                map.get("transaction_id"));
                            }
                        }
                        if (result.isSuccess() == false) {
                            System.out.println("超时，取消订单");
                            //2.调用删除
                            seckillOrderService.deleteOrderFromRedis(userId,
                                    Long.valueOf(out_trade_no));
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = new Result(false, "程序报错");
                    break;
                }
            }
        }
        return result;
    }

}
