package cn.kgc.itrip.biz.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.order.ItripAddHotelOrderVO;
import cn.kgc.itrip.beans.vo.order.ItripSearchOrderVO;
import cn.kgc.itrip.beans.vo.order.ValidateRoomStoreVO;
import cn.kgc.itrip.biz.service.IOderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:20
 */


@Controller
@RequestMapping(value = "/api/hotelorder")
public class HotelOrderController {
    @Resource
    private IOderService iOrderService;

    @RequestMapping(value = "/getpreorderinfo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getpreorderinfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO,
                                          HttpServletRequest servletRequest) {
        return iOrderService.queryStore(validateRoomStoreVO, servletRequest.getHeader("token"));
    }


    @RequestMapping(value = "/addhotelorder", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addOrder(@RequestBody ItripAddHotelOrderVO itripAddHotelOrderVO, HttpServletRequest httpServletRequest) {
        return iOrderService.addOrder(itripAddHotelOrderVO, httpServletRequest.getHeader("token"));
    }

    @RequestMapping(value = "/queryOrderById/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse queryOrderByid(@PathVariable Integer orderId) {
        return iOrderService.queryOrderById(orderId);
    }

    @RequestMapping(value = "/getpersonalorderroominfo/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse queryRoomDetail(@PathVariable Integer orderId) {
        return iOrderService.queryRoomDetail(orderId);
    }

    @RequestMapping(value = "/getpersonalorderinfo/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getpersonalorderinfo(@PathVariable Integer orderId) {
        return iOrderService.getpersonalorderinfo(orderId);
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        System.out.println("任务调度测试......" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * 更改订单状态，取消
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateOrderStatus() {
        iOrderService.updateOrderStatus(1);
    }

    /**
     * 更改订单状态，成功
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateOrderStatusSuccess() {
        iOrderService.updateOrderStatus(2);
    }

    @RequestMapping(value = "/getpersonalorderlist", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getpersonalorderlist(@RequestBody ItripSearchOrderVO itripSearchOrderVO, HttpServletRequest request) {
        return iOrderService.queryOrderList(itripSearchOrderVO, request.getHeader("token"));
    }
}
