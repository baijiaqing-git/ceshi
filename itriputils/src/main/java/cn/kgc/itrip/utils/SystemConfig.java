package cn.kgc.itrip.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 韩宏伟
 * 类名： SystemConfig.java
 * 创建时间： 2019/9/23 16:37
 * @version 1.0
 */
@Component
@Data
public class SystemConfig {

    /**
     * 订单流程成功
     */
    private String orderProcessOK;

    /**
     * 订单取消
     */
    private String orderProcessCancel;

    /**
     * 短信平台账户 ACCOUNT SID
     */
    private String smsAccountSid;
    /**
     * 短信平台账户 ACCOUNT TOKEN
     */
    private String smsAuthToken;
    /**
     * 短信平台账户 APP ID
     */
    private String smsAppID;
    /**
     * 短信平台账户 serverIp
     */
    private String smsServerIP;

    /**
     * 短信平台账户 server Port
     */
    private String smsServerPort;
    //发件人地址
    private String senderAddress;
    //发件人账户名
    private String senderAccount;
    //授权码
    private String senderPassword;
    /**
     * 文件上传路径，通过properties文件进行配置
     */
    private String fileUploadPathString;
    /**
     * 上传文件访问URL，通过properties文件进行配置
     */
    private String visitImgUrlString;
    private String machineCode;


    /**
     * 在线支付交易完成通知后续处理接口的地址
     */
    private String tradeEndsUrl;

    /**
     * 支付模块发送Get请求是否使用代理
     */
    private Boolean tradeUseProxy;
    /**
     * 代理主机
     */
    private String tradeProxyHost;
    /**
     * 代理端口
     */
    private Integer tradeProxyPort;

    public String getTradeEndsUrl() {
        return tradeEndsUrl;
    }

    public void setTradeEndsUrl(String tradeEndsUrl) {
        this.tradeEndsUrl = tradeEndsUrl;
    }

    public Boolean getTradeUseProxy() {
        return tradeUseProxy;
    }

    public void setTradeUseProxy(Boolean tradeUseProxy) {
        this.tradeUseProxy = tradeUseProxy;
    }

    public String getTradeProxyHost() {
        return tradeProxyHost;
    }

    public void setTradeProxyHost(String tradeProxyHost) {
        this.tradeProxyHost = tradeProxyHost;
    }

    public Integer getTradeProxyPort() {
        return tradeProxyPort;
    }

    public void setTradeProxyPort(Integer tradeProxyPort) {
        this.tradeProxyPort = tradeProxyPort;
    }

    public String getFileUploadPathString() {
        return fileUploadPathString;
    }

    public void setFileUploadPathString(String fileUploadPathString) {
        this.fileUploadPathString = fileUploadPathString;
    }

    public String getVisitImgUrlString() {
        return visitImgUrlString;
    }

    public void setVisitImgUrlString(String visitImgUrlString) {
        this.visitImgUrlString = visitImgUrlString;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getOrderProcessOK() {
        return orderProcessOK;
    }

    public void setOrderProcessOK(String orderProcessOK) {
        this.orderProcessOK = orderProcessOK;
    }

    public String getOrderProcessCancel() {
        return orderProcessCancel;
    }

    public void setOrderProcessCancel(String orderProcessCancel) {
        this.orderProcessCancel = orderProcessCancel;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSmsAccountSid() {
        return smsAccountSid;
    }

    public void setSmsAccountSid(String smsAccountSid) {
        this.smsAccountSid = smsAccountSid;
    }

    public String getSmsAuthToken() {
        return smsAuthToken;
    }

    public void setSmsAuthToken(String smsAuthToken) {
        this.smsAuthToken = smsAuthToken;
    }

    public String getSmsAppID() {
        return smsAppID;
    }

    public void setSmsAppID(String smsAppID) {
        this.smsAppID = smsAppID;
    }

    public String getSmsServerIP() {
        return smsServerIP;
    }

    public void setSmsServerIP(String smsServerIP) {
        this.smsServerIP = smsServerIP;
    }

    public String getSmsServerPort() {
        return smsServerPort;
    }

    public void setSmsServerPort(String smsServerPort) {
        this.smsServerPort = smsServerPort;
    }

}
