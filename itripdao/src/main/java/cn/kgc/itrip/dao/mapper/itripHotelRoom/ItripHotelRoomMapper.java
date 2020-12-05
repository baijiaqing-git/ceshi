package cn.kgc.itrip.dao.mapper.itripHotelRoom;

import cn.kgc.itrip.beans.model.ItripHotelRoom;
import cn.kgc.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.kgc.itrip.beans.vo.mappervo.HotelRoomMapperVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripHotelRoomMapper {

    /**
     * 查询酒店房型
     * @param hotelRoomMapperVo
     * @return
     */
    public List<ItripHotelRoomVO>queryroombed(HotelRoomMapperVo hotelRoomMapperVo);

    public ItripHotelRoom getItripHotelRoomById(@Param(value = "id") Long id);
}

