package cn.kgc.itrip.biz.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.biz.service.IHotelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 21:29
 */
@Controller
@RequestMapping("/api/hotel")
public class HotelController {
    @Resource
    private IHotelService iHotelService;

    /**
     * 查询热门城市
     *
     * @param type
     * @return
     */
    @RequestMapping("/queryhotcity/{type}")
    @ResponseBody
    public ServerResponse queryhotcity(@PathVariable Integer type) {
        return iHotelService.getHotCity(type);
    }

    /**
     * 查询酒店特色列表
     *
     * @return
     */
    @RequestMapping("queryhotelfeature")
    @ResponseBody
    public ServerResponse queryhotelfeature() {
        return iHotelService.getHotelFeature();
    }

    /**
     * 酒店设施
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryhotelfacilities/{id}")
    @ResponseBody
    public ServerResponse gethotelfacilities(@PathVariable Integer id) throws Exception {
        return iHotelService.gethotelfacilities(id);
    }

    /**
     * 酒店特色和介绍
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryhoteldetails/{id}")
    @ResponseBody
    public ServerResponse queryhoteldetails(@PathVariable Integer id) throws Exception {
        return iHotelService.itripSearchDetailsHotelVO(id);
    }

    /**
     * 查询商圈
     * @param cityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querytradearea/{cityId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse querytradearea(@PathVariable Integer cityId) throws Exception {
        return iHotelService.querytradearea(cityId);
    }

    /**
     * 根据酒店id查询酒店政策
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryhotelpolicy/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse queryhotelpolicy(@PathVariable Integer id)throws Exception{
        return iHotelService.queryhotelpolicy(id);
    }

    /**
     *  根据酒店id查询酒店特色、商圈、酒店名称
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getvideodesc/{hotelId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getvideodesc(@PathVariable Integer hotelId)throws  Exception{
        return iHotelService.getvideodesc(hotelId);
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
        return iHotelService.getImg(targetId);
    }
}
