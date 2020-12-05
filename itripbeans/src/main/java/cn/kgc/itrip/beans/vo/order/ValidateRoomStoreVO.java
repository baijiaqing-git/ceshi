package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class ValidateRoomStoreVO {

    private Date checkInDate;
    private Date checkOutDate;
    private Integer count;
    private Long hotelId;
    private Long roomId;

}
