package cn.kgc.itrip.biz.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.hotelroom.SearchHotelRoomVO;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 12:04
 */
public interface ItripHotelRoomService {

    /**
     * 查询酒店房型列表
     * @param searchHotelRoomVO
     * @return
     */
    public ServerResponse queryhotelroombyhotel(SearchHotelRoomVO searchHotelRoomVO);

    public ServerResponse getImg(Integer targetId)throws Exception;

    public ServerResponse queryhotelroombed()throws Exception;

}
