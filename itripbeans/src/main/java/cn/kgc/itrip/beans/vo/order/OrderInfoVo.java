package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class OrderInfoVo {

    private Integer bookType;
    private Integer bookingDays;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer count;
    private Long hotelId;
    private String hotelName;
    private Long id;
    private String invoiceHear;
    private Integer invoiceType;
    private Integer isNeedInvoice;
    private OrderInfoInLinkUser[] itripOrderLinkUserList;
    private String linkUserName;
    private String noticeEmail;
    private String noticePhone;
    private String orderNo;
    private Integer orderType;
    private Integer payType;
    private Long roomId;
    private String specialRequirement;
}
