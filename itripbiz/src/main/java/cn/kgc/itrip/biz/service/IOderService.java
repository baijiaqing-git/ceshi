package cn.kgc.itrip.biz.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.order.ItripAddHotelOrderVO;
import cn.kgc.itrip.beans.vo.order.ItripSearchOrderVO;
import cn.kgc.itrip.beans.vo.order.ValidateRoomStoreVO;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:02
 */
public interface IOderService {
    public ServerResponse queryStore(ValidateRoomStoreVO validateRoomStoreVO, String token) ;

    public ServerResponse addOrder(ItripAddHotelOrderVO itripAddHotelOrderVO, String token);

    public ServerResponse queryOrderById(Integer orderId);

    public ServerResponse queryRoomDetail(Integer orderId);

    public ServerResponse getpersonalorderinfo(Integer orderId);

    public void updateOrderStatus(Integer type) ;

    public ServerResponse queryOrderList(ItripSearchOrderVO itripSearchOrderVO, String token);

}
