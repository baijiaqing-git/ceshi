package cn.kgc.itrip.biz.service.impl;

import cn.kgc.itrip.beans.common.Const;
import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.*;
import cn.kgc.itrip.beans.vo.order.*;
import cn.kgc.itrip.biz.service.IOderService;
import cn.kgc.itrip.dao.mapper.itripHotel.ItripHotelMapper;
import cn.kgc.itrip.dao.mapper.itripHotelOrder.ItripHotelOrderMapper;
import cn.kgc.itrip.dao.mapper.itripHotelRoom.ItripHotelRoomMapper;
import cn.kgc.itrip.dao.mapper.itripProductStore.ItripProductStoreMapper;
import cn.kgc.itrip.utils.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:03
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOderService {
    @Resource
    private RedisApi redisApi;

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Resource
    private ItripHotelMapper itripHotelMapper;

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    private ItripProductStoreMapper itripProductStoreMapper;

    public ServerResponse queryStore(ValidateRoomStoreVO validateRoomStoreVO, String token) {
        //校验用户是否存在
        if (StringUtils.isBlank(token)) {
            log.error("参数丢失");
            return ServerResponse.createByErrorMessage("用户令牌丢失");
        }
        if (!redisApi.exists(token)) {
            log.error("参数丢失");
            return ServerResponse.createByErrorMessage("用户需要重新登录");
        }
        //刷新库存
        Map<String, Object> flushMap = new HashMap<>();
        flushMap.put("startTime", validateRoomStoreVO.getCheckInDate());
        flushMap.put("endTime", validateRoomStoreVO.getCheckOutDate());
        flushMap.put("roomId", validateRoomStoreVO.getRoomId());
        flushMap.put("hotelId", validateRoomStoreVO.getHotelId());
        itripHotelOrderMapper.flushStore(flushMap);
        //查库存
        Map<String, Object> map = new HashMap<>();
        map.put("checkInDate", validateRoomStoreVO.getCheckInDate());
        map.put("checkOutDate", validateRoomStoreVO.getCheckOutDate());
        map.put("roomId", validateRoomStoreVO.getRoomId());
        RoomStore roomStore = itripHotelOrderMapper.queryStore(map);

        StoreInfoVo storeInfoVo = new StoreInfoVo();
        //获取酒店信息
        ItripHotel itriphotelById = itripHotelMapper.getItripHotelById(validateRoomStoreVO.getHotelId());
        //获取房间信息
        ItripHotelRoom itripHotelRoomById = itripHotelRoomMapper.getItripHotelRoomById(validateRoomStoreVO.getRoomId());
        storeInfoVo.setHotelName(itriphotelById.getHotelName());
        storeInfoVo.setPrice(itripHotelRoomById.getRoomPrice());
        storeInfoVo.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
        storeInfoVo.setCheckInDate(validateRoomStoreVO.getCheckInDate());
        storeInfoVo.setHotelId(validateRoomStoreVO.getHotelId());
        storeInfoVo.setRoomId(validateRoomStoreVO.getRoomId());
        storeInfoVo.setCount(validateRoomStoreVO.getCount());
        if (roomStore == null) {
            storeInfoVo.setStore(itripProductStoreMapper.queryStoreByRoomId(validateRoomStoreVO.getRoomId().intValue()));
        } else {
            storeInfoVo.setStore(roomStore.getStore());
        }
        return ServerResponse.createBySuccess(storeInfoVo);
    }

    /**
     * 添加订单
     *
     * @param itripAddHotelOrderVO
     * @param token
     * @return
     */
    @Override
    public ServerResponse addOrder(ItripAddHotelOrderVO itripAddHotelOrderVO, String token) {
        //校验参数
        if (StringUtils.isBlank(token)) {
            log.error("添加订单时，用户令牌丢失......");
            return ServerResponse.createByErrorMessage("参数丢失");
        }
        if (!redisApi.exists(token)) {
            log.error("用户令牌丢失");
            return ServerResponse.createByErrorMessage("用户令牌失效,请重新登录");
        }
        //刷新库存表
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", itripAddHotelOrderVO.getCheckInDate());
        map.put("endTime", itripAddHotelOrderVO.getCheckOutDate());
        map.put("roomId", itripAddHotelOrderVO.getRoomId());
        map.put("HotelId", itripAddHotelOrderVO.getHotelId());
        itripHotelOrderMapper.flushStore(map);

        //查询酒店库存
        Map<String, Object> queryStore = new HashMap<>();
        queryStore.put("roomId", itripAddHotelOrderVO.getRoomId());
        queryStore.put("checkInDate", itripAddHotelOrderVO.getCheckInDate());
        queryStore.put("checkOutDate", itripAddHotelOrderVO.getCheckOutDate());
        RoomStore roomStore = itripHotelOrderMapper.queryStore(queryStore);

        //
        if (roomStore == null) {
            //满库存
        } else {
            if (roomStore.getStore() < itripAddHotelOrderVO.getCount()) {
                return ServerResponse.createByErrorMessage("库存不足。。。。");
            }
        }
        //库存大于需求
        ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
        StringBuffer orderNo = new StringBuffer();
        orderNo.append("D");
        orderNo.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        orderNo.append(StringUtils.substring(MD5Util.MD5EncodeUtf8(token), 0, 6));
        itripHotelOrder.setOrderNo(orderNo.toString());
        //生成交易号
        StringBuffer tradeNo = new StringBuffer();
        tradeNo.append(System.currentTimeMillis());
        tradeNo.append(Math.random() * 10);
        itripHotelOrder.setTradeNo(tradeNo.toString());
        //产品类型
        itripHotelOrder.setOrderType(1);
        itripHotelOrder.setHotelId(itripAddHotelOrderVO.getHotelId());
        itripHotelOrder.setHotelName(itripAddHotelOrderVO.getHotelName());
        //设置入住天数
        itripHotelOrder.setBookingDays(DateUtil.getBetweenDates(
                itripAddHotelOrderVO.getCheckInDate(), itripAddHotelOrderVO.getCheckOutDate()).size() - 1);
        //
        String bookModel = StringUtils.substring(token, 6, 7);
        switch (bookModel) {
            case "P":
                itripHotelOrder.setBookType(0);
                break;
            case "M":
                itripHotelOrder.setBookType(1);
                break;
            default:
                itripHotelOrder.setBookType(2);
        }
        itripHotelOrder.setCheckInDate(itripAddHotelOrderVO.getCheckInDate());
        itripHotelOrder.setCheckOutDate(itripAddHotelOrderVO.getCheckOutDate());
        itripHotelOrder.setCount(itripAddHotelOrderVO.getCount());
        itripHotelOrder.setInvoiceHead(itripAddHotelOrderVO.getInvoiceHead());
        itripHotelOrder.setInvoiceType(itripAddHotelOrderVO.getInvoiceType());
        ItripUser itripUser = JSONObject.parseObject(redisApi.get(token), ItripUser.class);
        itripHotelOrder.setUserId(itripUser.getId());
        itripHotelOrder.setCreatedBy(itripUser.getId());
        itripHotelOrder.setIsNeedInvoice(itripAddHotelOrderVO.getIsNeedInvoice());
        //拼接订单联系人
        StringBuffer linkUser = new StringBuffer();
        for (ItripUserLinkUser name : itripAddHotelOrderVO.getLinkUser()) {
            linkUser.append(name.getLinkUserName() + ",");
        }
        itripHotelOrder.setLinkUserName(linkUser.toString());
        itripHotelOrder.setNoticeEmail(itripAddHotelOrderVO.getNoticeEmail());
        itripHotelOrder.setNoticePhone(itripAddHotelOrderVO.getNoticePhone());
        itripHotelOrder.setPayType(Const.PayPlatformEnum.ALIPAY.getCode());
        itripHotelOrder.setCreationDate(new Date());
        itripHotelOrder.setRoomId(itripAddHotelOrderVO.getRoomId());
        itripHotelOrder.setOrderStatus(Const.OrderStatusEnum.TO_BE_PAID.getCode());
        //计算金额
        //获取房间单价
        ItripHotelRoom itripHotelRoomById
                = itripHotelRoomMapper.getItripHotelRoomById(itripAddHotelOrderVO.getRoomId());

        itripHotelOrder.setPayAmount(BigDecimalUtilS.mul(BigDecimalUtilS.mul(
                itripAddHotelOrderVO.getCount(),
                itripHotelRoomById.getRoomPrice()).doubleValue(),
                itripHotelOrder.getBookingDays()).doubleValue());
        itripHotelOrder.setSpecialRequirement(itripAddHotelOrderVO.getSpecialRequirement());
        //添加订单
        itripHotelOrderMapper.addOrder(itripHotelOrder);
        AddOrderSuccessVo addOrderSuccessVo = new AddOrderSuccessVo();
        addOrderSuccessVo.setId(itripHotelOrder.getId());
        addOrderSuccessVo.setOrderNo(orderNo.toString());
        return ServerResponse.createBySuccess(addOrderSuccessVo);
    }

    /**
     * 根据订单id查询订单表
     *
     * @param orderId
     * @return
     */
    @Override
    public ServerResponse queryOrderById(Integer orderId) {
        OrderInfoVo orderInfoVo = itripHotelOrderMapper.queryOrderById(orderId);
        if (orderInfoVo == null) {
            log.error("查询订单信息失败......");
            return ServerResponse.createByErrorMessage("查询订单信息失败");
        }
        return ServerResponse.createBySuccess(orderInfoVo);
    }

    @Override
    public ServerResponse queryRoomDetail(Integer orderId) {
        OrderRoomDetailVo orderRoomDetailVo = itripHotelOrderMapper.queryRoomDetailById(orderId);
        if (orderRoomDetailVo == null) {
            log.error("查询订单房间详情失败");
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess(orderRoomDetailVo);
    }


    @Resource
    private SystemConfig systemConfig;

    @Override
    public ServerResponse getpersonalorderinfo(Integer orderId) {
        OrderDetailVo getpersonalorderinfo = itripHotelOrderMapper.getpersonalorderinfo(orderId);
        //todo 订单状态（0：待支付 1:已取消 2:支付成功 3:已消费 4:已点评）
        // todo{"1":"订单提交","2":"订单支付","3":"支付成功","4":"入住","5":"订单点评","6":"订单完成"}
        // todo{"1":"订单提交","2":"订单支付","3":"订单取消"}
        if (getpersonalorderinfo.getOrderStatus().equals(Const.OrderStatusEnum.TO_BE_PAID.getCode())){
            //待支付
            getpersonalorderinfo.setOrderProcess(JSONObject.parse(systemConfig.getOrderProcessOK()));
            getpersonalorderinfo.setProcessNode("2");
        }
        if (getpersonalorderinfo.getOrderStatus().equals(Const.OrderStatusEnum.CANCELLED.getCode())){
            //已取消
            getpersonalorderinfo.setOrderProcess(JSONObject.parse(systemConfig.getOrderProcessCancel()));
            getpersonalorderinfo.setProcessNode("3");
        }
        if (getpersonalorderinfo.getOrderStatus().equals(Const.OrderStatusEnum.SUCCESSFUL_PAYMENT.getCode())){
            //支付成功
            getpersonalorderinfo.setOrderProcess(JSONObject.parse(systemConfig.getOrderProcessOK()));
            getpersonalorderinfo.setProcessNode("4");
        }
        if (getpersonalorderinfo.getOrderStatus().equals(Const.OrderStatusEnum.CONSUMED.getCode())){
            //以消费
            getpersonalorderinfo.setOrderProcess(JSONObject.parse(systemConfig.getOrderProcessOK()));
            getpersonalorderinfo.setProcessNode("5");
        }
        if (getpersonalorderinfo.getOrderStatus().equals(Const.OrderStatusEnum.ORDER_SUCCESS.getCode())){
            //已点评
            getpersonalorderinfo.setOrderProcess(JSONObject.parse(systemConfig.getOrderProcessOK()));
            getpersonalorderinfo.setProcessNode("6");
        }

        return ServerResponse.createBySuccess(getpersonalorderinfo);
    }

    @Override
    public void updateOrderStatus(Integer type) {
        if (type == 1) {
            //修改已经取消的订单状态
            Integer rowCount = itripHotelOrderMapper.updateOrderStatusCancel(
                    Const.OrderStatusEnum.TO_BE_PAID.getCode(),
                    Const.OrderStatusEnum.CANCELLED.getCode());
            log.error("》》》》》》》》》》》》更新订单状态为取消,数量为:" + rowCount);
        } else if (type == 2) {
            //修改已消费的订单状态
            Integer rowCount = itripHotelOrderMapper.updateOrderStatusSucess();
            log.error("》》》》》》》》》》》》更新订单状态为已消费,数量为:" + rowCount);
        }
    }

    @Override
    public ServerResponse queryOrderList(ItripSearchOrderVO itripSearchOrderVO, String token) {
        if (StringUtils.isBlank(token)) {
            log.error("用户令牌丢失");
            return ServerResponse.createByErrorMessage("用户令牌丢失");
        }
        if (!redisApi.exists(token)) {
            return ServerResponse.createByErrorMessage("用户令牌丢失");
        }
        ItripUser itripUser = JSONObject.parseObject(redisApi.get(token), ItripUser.class);
        itripSearchOrderVO.setUserId(itripUser.getId().intValue());
        Page page = new Page(itripSearchOrderVO.getPageNo(), itripSearchOrderVO.getPageSize(),
                itripHotelOrderMapper.queryPageCount(itripSearchOrderVO));
        itripSearchOrderVO.setPageNo(page.getBeginPos());
        itripSearchOrderVO.setPageSize(page.getPageSize());

        List<OrderListVo> orderListVos = itripHotelOrderMapper.queryOrderList(itripSearchOrderVO);
        page.setRows(orderListVos);
        return ServerResponse.createBySuccess(page);
    }
}