package com.pyg.pay.service;

import java.util.Map;

public interface WeixinPayService {

    /**
     * 生成微信支付二维码
     *
     * @param out_trade_no 订单号
     * @param total_fee    金额(分)
     * @return
     */
    public Map createNative(String out_trade_no, String total_fee);

    public Map queryPayStatus(String out_trade_no);
    public Map closePay(String out_trade_no);
}
