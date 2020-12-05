package cn.kgc.itrip.dao.mapper.itripHotelOrder;

import cn.kgc.itrip.beans.model.ItripHotelOrder;
import cn.kgc.itrip.beans.vo.order.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripHotelOrderMapper {
    public ItripHotelOrder getItripHotelOrderById(@Param(value = "id") Long id) throws Exception;

    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception;

    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception;

    public Integer insertItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public Integer updateItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception;

    public Integer deleteItripHotelOrderById(@Param(value = "id") Long id) throws Exception;

    /**
     * 刷新库存
     *
     * @param map
     */
    public void flushStore(Map<String, Object> map);

    /**
     * 查询指定房间的库存
     *
     * @param map
     * @return
     */
    public RoomStore queryStore(Map<String, Object> map);

    /**
     * 添加订单
     *
     * @param itripHotelOrder
     */
    public Integer addOrder(ItripHotelOrder itripHotelOrder);

    /**
     * 根据订单信息查询订单详情
     *
     * @param orderId
     * @return
     */
    OrderInfoVo queryOrderById(Integer orderId);

    /**
     * 查询
     *
     * @param orderId
     * @return
     */
    OrderRoomDetailVo queryRoomDetailById(Integer orderId);

    /**
     * 查询个人订单详情
     *
     * @param orderId
     * @return
     */
    OrderDetailVo getpersonalorderinfo(Integer orderId);

    /**
     * 更新状态
     *
     * @param currentStatus 当前状态
     * @param status        即将要更新的状态
     * @return
     */
    Integer updateOrderStatusCancel(@Param("currentStatus") Integer currentStatus, @Param("status") Integer status);

    /**
     * 修改订单已消费
     *
     * @return
     */
    Integer updateOrderStatusSucess();

    /**
     * 查询个人订单列表
     *
     * @param itripSearchOrderVO
     * @return
     */
    List<OrderListVo> queryOrderList(ItripSearchOrderVO itripSearchOrderVO);

    /**
     * 查询符合条件的数量
     *
     * @param itripSearchOrderVO
     * @return
     */
    Integer queryPageCount(ItripSearchOrderVO itripSearchOrderVO);

    ItripHotelOrder queryOrderByUserCodeAndOrderNo(@Param("userCode") String userCode,
                                                   @Param("orderNo") String orderNo);

    /**
     * 支付成功后修改订单状态
     * @param tradeNo
     * @return
     */
    Integer updateOrderStatusByPayed(String tradeNo);

    /**
     * 根据订单号查询订单信息
     * @param tradeNo
     * @return
     */
    ItripHotelOrder queryOrderByTradeNo(String tradeNo);

}
