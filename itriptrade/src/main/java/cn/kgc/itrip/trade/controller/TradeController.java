package cn.kgc.itrip.trade.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripHotelOrder;
import cn.kgc.itrip.dao.mapper.itripHotelOrder.ItripHotelOrderMapper;
import cn.kgc.itrip.trade.config.AlipayConfig;
import cn.kgc.itrip.trade.service.ITradeService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/11/1 16:23
 */
@Controller
@Slf4j
@RequestMapping("/api")
public class TradeController {
    @Resource
    private ITradeService iTradeService;

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @RequestMapping("/prepay/{orderNo}")
    public void prepay(@PathVariable String orderNo, HttpServletRequest request, HttpServletResponse response) {
        //TODO 查询订单号，确定当前用户拥有此订单
        Cookie[] cookies = request.getCookies();
        String currentUser = null;
        //TODO 获取当前用户的用户名
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
            if (StringUtils.equals(cookie.getName(), "user")) {
                System.out.println(cookie.getValue());
                currentUser = cookie.getValue();
            }
        }
        ServerResponse serverResponse =
                iTradeService.queryOrderByUserCodeAndOrderNo(currentUser, orderNo);
        if (serverResponse.isSuccess()) {
            //TODO 用户拥有订单
            //TODO 开始组装阿里支付所需要的参数
            AlipayClient alipayClient = new DefaultAlipayClient(
                    alipayConfig.getUrl(),
                    alipayConfig.getAppID(),
                    alipayConfig.getRsaPrivateKey(),
                    alipayConfig.getFormat(),
                    alipayConfig.getCharset(),
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getSignType());
            //设置请求参数
            AlipayTradePagePayRequest alipatRequerst = new AlipayTradePagePayRequest();
            //设置同步回调
            alipatRequerst.setReturnUrl(alipayConfig.getReturnUrl());

            //设置异步回调
            alipatRequerst.setNotifyUrl(alipayConfig.getNotifyUrl());
            ItripHotelOrder itripHotelOrder = (ItripHotelOrder) serverResponse.getData();
            String out_trade_no = itripHotelOrder.getTradeNo();
            String total_amount = itripHotelOrder.getPayAmount() + "";
            String subject = itripHotelOrder.getOrderNo();
            String body = itripHotelOrder.getHotelName() + "房间预订";
            alipatRequerst.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"timeout_express\":\"10m\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //TODO 调起支付
            try {
                String result = alipayClient.pageExecute(alipatRequerst).getBody();
                response.setContentType("text/html;charset=" + alipayConfig.getCharset());
                response.getWriter().print(result);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (AlipayApiException e) {
                System.out.println("支付宝发生异常");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public void payreturn(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, String> param = Maps.newHashMap();
        Map parameterMap = request.getParameterMap();
        for (Object o : parameterMap.keySet()) {
            String name = (String) o;
            String[] values = (String[]) parameterMap.get(name);
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < values.length; i++) {
                str = (i == values.length - 1) ? str.append(values[i]) : str.append(values[i] + ",");

            }
            param.put(name, str.toString());
        }

        //删除不需要的参数

        param.remove("sign_type");

        try {
            boolean checkV2 = AlipaySignature.rsaCheckV2(param,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType());
            //成功
            System.out.println("-------------------" + checkV2);
            ItripHotelOrder itripHotelOrder = itripHotelOrderMapper.queryOrderByTradeNo(param.get("out_trade_no"));

            try {
                response.sendRedirect(String.format(alipayConfig.getPaymentSuccessUrl(),
                        itripHotelOrder.getOrderNo(),
                        itripHotelOrder.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("阿里同步回调.......");
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request) {
        HashMap<String, String> param = Maps.newHashMap();
        Map parameterMap = request.getParameterMap();
        for (Object o : parameterMap.keySet()
        ) {
            String name = (String) o;
            String[] values = (String[]) parameterMap.get(name);
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < values.length; i++) {
                str = (i == values.length - 1) ? str.append(values[i]) : str.append(values[i] + ",");
            }
            param.put(name, str.toString());
        }

        //删除不需要的参数
        param.remove("sign_type");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(param);
        try {
            boolean checkV2 = AlipaySignature.rsaCheckV2(param,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType());
            log.info("》》》》》》》》》》" + checkV2);
            // todo 确定为支付宝回调
            iTradeService.changeOrderStatus(param.get("out_trade_no"));
            log.info("》》》》》》》》》》订单状态更新完成");
            // todo 不是支付宝回调
            log.error("》》》》》》》》》收到来自非支付宝的异步回调");
        } catch (AlipayApiException e) {
            System.out.println("支付宝报错");
            e.printStackTrace();
        }
        System.out.println("阿里异步回调......." + new Date());
    }
}
