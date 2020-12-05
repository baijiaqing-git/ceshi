package cn.kgc.itrip.beans.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
*   
*/
@Data
public class ItripHotelTradingArea implements Serializable {
    /**
    * 
    */
    private Long id;
    /**
    * 酒店id
    */
    private Long hotelId;
    /**
    * 商圈id
    */
    private Long areaId;
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
