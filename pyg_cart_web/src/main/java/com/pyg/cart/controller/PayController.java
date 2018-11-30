package com.pyg.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.order.service.OrderService;
import com.pyg.pay.service.WeixinPayService;
import com.pyg.pojo.TbPayLog;
import com.pyg.utils.IdWorker;
import entity.Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;
    @Reference
    private OrderService orderService;


    @RequestMapping("getNative")
    public Map getNative() {
        String userId = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
        if (payLog != null) {
            IdWorker idworker = new IdWorker();
            return weixinPayService.createNative(idworker.nextId() + "", "1");
        }
        return new HashMap();
    }

    @RequestMapping("queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        Result result = new Result(false, "支付失败");
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
                        break;
                    }

                    Thread.sleep(3000);//间隔三秒

                    n++;
                    if (n > 40) {
                        result = new Result(false, "支付超时");
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
