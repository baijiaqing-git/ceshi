package cn.kgc.itrip.trade.service;

import cn.kgc.itrip.beans.common.ServerResponse;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:23
 */
public interface ITradeService {
    public ServerResponse queryOrderByUserCodeAndOrderNo(String userCode, String orderNo);
    public void changeOrderStatus(String tradeNo);
}
