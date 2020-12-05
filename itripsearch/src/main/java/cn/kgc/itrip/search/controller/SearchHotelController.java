package cn.kgc.itrip.search.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.kgc.itrip.beans.vo.hotel.SearchHotelVO;
import cn.kgc.itrip.search.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:58
 */
@Controller
@RequestMapping("/api/hotellist")
public class SearchHotelController {
    @Resource
    private ISearchService iSearchService;

    /**
     *根据热门城市查询酒店
     * @param searchHotCityVO
     * @return
     */
    @RequestMapping("/searchItripHotelListByHotCity")
    @ResponseBody
    public ServerResponse searchItripHotelListHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
        return iSearchService.queryHotCityHotel(searchHotCityVO);
    }

    /**
     * 查询酒店分页
     * @param searchHotelVO
     * @return
     */
    @RequestMapping(value = "/searchItripHotelPage",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse searchItriphotelPage(@RequestBody SearchHotelVO searchHotelVO){
        return iSearchService.searchItripHotelPage(searchHotelVO);
    }
}
