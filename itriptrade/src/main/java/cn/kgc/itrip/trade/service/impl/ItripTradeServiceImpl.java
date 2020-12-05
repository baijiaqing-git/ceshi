package cn.kgc.itrip.trade.service.impl;

import cn.kgc.itrip.beans.common.Const;
import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripHotelOrder;
import cn.kgc.itrip.dao.mapper.itripHotelOrder.ItripHotelOrderMapper;
import cn.kgc.itrip.trade.service.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:23
 */
@Service("ITradeService")
@Slf4j
public class ItripTradeServiceImpl implements ITradeService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Override
    public ServerResponse queryOrderByUserCodeAndOrderNo(String userCode, String orderNo){
        if (StringUtils.isBlank(userCode) || StringUtils.isBlank(orderNo)){
            log.error("参数丢失....");
            return ServerResponse.createByError();
        }
        ItripHotelOrder itripHotelOrder = itripHotelOrderMapper.queryOrderByUserCodeAndOrderNo(userCode, orderNo);
        if (itripHotelOrder == null){
            log.error("当前用户不拥有此订单");
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess(itripHotelOrder);
    }
    @Override
    public void changeOrderStatus(String tradeNo){
        if (org.apache.commons.lang3.StringUtils.isBlank(tradeNo)){
            log.error("在更新订单状态时，收到了错误的参数.....");
            return;
        }
        ItripHotelOrder itripHotelOrder = itripHotelOrderMapper.queryOrderByTradeNo(tradeNo);
        if (itripHotelOrder == null){
            log.error("订单号不存在.......");
            return;
        }
        if (itripHotelOrder.getOrderStatus() >= Const.OrderStatusEnum.SUCCESSFUL_PAYMENT.getCode()){
            log.error("当前订单状态已变更，阿里重复回调，不予理睬");
            return;
        }
        //更改订单状态
        itripHotelOrderMapper.updateOrderStatusByPayed(tradeNo);
    }
}
