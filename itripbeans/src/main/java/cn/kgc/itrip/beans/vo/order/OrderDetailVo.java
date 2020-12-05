package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class OrderDetailVo {

    private Integer bookType;
    private Date creationDate;
    private Long id;
    private String noticePhone;
    private String orderNo;
    private Object orderProcess;
    private Integer orderStatus;
    private double payAmount;
    private Integer payType;
    private String processNode;
    private Integer roomPayType;
}
