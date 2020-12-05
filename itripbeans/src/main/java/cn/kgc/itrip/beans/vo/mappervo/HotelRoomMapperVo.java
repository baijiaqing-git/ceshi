package cn.kgc.itrip.beans.vo.mappervo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HotelRoomMapperVo {
    private Long hotelId;
    private Integer isBook;
    private Integer isHavingBreakfast;
    private Integer isTimelyResponse;
    private Long roomBedTypeId;
    private Integer isCancel;
    private Integer payType;
    private List<Date> originalRoomList;
}
