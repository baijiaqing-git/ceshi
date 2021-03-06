package cn.kgc.itrip.beans.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
*   
*/
@Data
public class ItripOrderLinkUser implements Serializable {
    /**
    * 主键
    */
    private Long id;
    /**
    * 订单id
    */
    private Long orderId;
    /**
    * 联系人id
    */
    private Long linkUserId;
    /**
    * 入住人姓名逗号分隔
    */
    private String linkUserName;
    /**
    * 
    */
    private Date creationDate;
    /**
    * 
    */
    private Long createdBy;
    /**
    * 
    */
    private Date modifyDate;
    /**
    * 
    */
    private Long modifiedBy;
}
