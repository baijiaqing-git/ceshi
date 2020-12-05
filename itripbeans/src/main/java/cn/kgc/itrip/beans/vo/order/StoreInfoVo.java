package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class StoreInfoVo {
    private Date checkInDate;
    private Date checkOutDate;
    private Integer count;
    private Long hotelId;
    private String hotelName;
    private Double price;
    private Long roomId;
    private Integer store;

}
