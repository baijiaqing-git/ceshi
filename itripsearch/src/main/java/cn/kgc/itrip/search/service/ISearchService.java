package cn.kgc.itrip.search.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import cn.kgc.itrip.beans.vo.hotel.SearchHotelVO;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:43
 */
public interface ISearchService {
    public ServerResponse queryHotCityHotel(SearchHotCityVO searchHotCityVO);

    public ServerResponse searchItripHotelPage(SearchHotelVO searchHotelVO);

}
