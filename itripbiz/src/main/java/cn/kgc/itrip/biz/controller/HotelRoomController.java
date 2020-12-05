package cn.kgc.itrip.biz.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.kgc.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import cn.kgc.itrip.beans.vo.mappervo.HotelRoomMapperVo;
import cn.kgc.itrip.biz.service.ItripHotelRoomService;
import cn.kgc.itrip.dao.mapper.itripHotelRoom.ItripHotelRoomMapper;
import cn.kgc.itrip.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 12:07
 */

@Controller
@RequestMapping(value = "/api/hotelroom")
public class HotelRoomController {

    @Resource
    private ItripHotelRoomService itripHotelRoomService;

    /**
     * 查询酒店房型
     * @param searchHotelRoomVO
     * @return
     */
    @RequestMapping(value = "/queryhotelroombyhotel",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse queryhotelroombyhotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO){
        return itripHotelRoomService.queryhotelroombyhotel(searchHotelRoomVO);
    }

    /**
     * 根据targetId查询酒店图片(type=0)
     * @param targetId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getimg/{targetId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getImg(@PathVariable Integer targetId)throws Exception{
        return itripHotelRoomService.getImg(targetId);
    }


    /**
     * 查询酒店房间床型列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryhotelroombed",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse queryhotelroombed()throws Exception{
        return itripHotelRoomService.queryhotelroombed();
    }
}

