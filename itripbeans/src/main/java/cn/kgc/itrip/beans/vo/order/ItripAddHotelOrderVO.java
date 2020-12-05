package cn.kgc.itrip.beans.vo.order;

import cn.kgc.itrip.beans.model.ItripUserLinkUser;
import lombok.Data;

import java.util.Date;


@Data
public class ItripAddHotelOrderVO {
    /**
     * 订单类型(0:旅游产品 1:酒店产品 2：机票产品)
     */
    private Integer orderType;
    /**
     * 冗余字段 酒店id
     */
    private Long hotelId;
    /**
     * 冗余字段 酒店名称
     */
    private String hotelName;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 消耗数量
     */
    private Integer count;
    /**
     * 入住时间
     */
    private Date checkInDate;
    /**
     * 退房时间
     */
    private Date checkOutDate;
    /**
     * 联系手机号
     */
    private String noticePhone;
    /**
     * 联系邮箱
     */
    private String noticeEmail;
    /**
     * 特殊需求
     */
    private String specialRequirement;
    /**
     * 是否需要发票（0：不需要 1：需要）
     */
    private Integer isNeedInvoice;
    /**
     * 发票类型（0：个人 1：公司）
     */
    private Integer invoiceType;
    /**
     * 发票抬头
     */
    private String invoiceHead;
    /**
     * 入住人名称
     */
    private ItripUserLinkUser[] linkUser;
    /**
     * 预定时间
     */
    private Date creationDate;
    /**
     *
     */
    private Long createdBy;
}
