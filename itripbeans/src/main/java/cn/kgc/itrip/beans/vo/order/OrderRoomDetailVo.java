package cn.kgc.itrip.beans.vo.order;

import lombok.Data;

import java.util.Date;


@Data
public class OrderRoomDetailVo {
    private String address;
    private Integer bookingDays;
    private Date checkInDate;
    private Integer checkInWeek;
    private Date checkOutDate;
    private Integer checkOutWeek;
    private Integer count;
    private Integer hotelId;
    private Integer hotelLevel;
    private String hotelName;
    private Integer id;
    private Integer isHavingBreakfast;
    private String linkUserName;
    private Double payAmount;
    private Integer roomBedTypeId;
    private String roomBedTypeName;
    private Integer roomId;
    private Integer roomPayType;
    private String roomTitle;
    private String specialRequirement;
}
