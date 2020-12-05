package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class ItripSearchOrderVO {

    private Date endDate;
    private String linkUserName;
    private String orderNo;
    private Integer orderStatus;
    private Integer orderType;
    private Integer pageNo;
    private Integer pageSize;
    private Date startDate;
    private Integer userId;

}
