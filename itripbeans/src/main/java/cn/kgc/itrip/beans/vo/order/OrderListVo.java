package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class OrderListVo {

    private Date checkInDate;
    private Date creationDate;
    private Integer hotelId;
    private String hotelName;
    private Long id;
    private String linkUserName;
    private String orderNo;
    private Integer orderStatus;
    private Integer orderType;
    private double payAmount;

}
