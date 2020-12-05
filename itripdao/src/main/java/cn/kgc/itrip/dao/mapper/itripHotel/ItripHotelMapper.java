package cn.kgc.itrip.dao.mapper.itripHotel;

import cn.kgc.itrip.beans.model.ItripHotel;
import cn.kgc.itrip.beans.vo.hotel.ItripSearchDetailsHotelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripHotelMapper {

    public String queryhotelfacilities(@Param(value = "id")Integer id)throws Exception;

    public String itripSearchDetailsHotelVO(Integer id)throws Exception;

    /**
     * 根据酒店Id查询酒店政策
     */
    public String queryhotelpolicy(Integer id)throws Exception;

    public List<ItripSearchDetailsHotelVO> itripSearchDetailsHotelVOs(Integer id)throws Exception;

    /**
     *根据酒店id查询酒店特色，商圈，酒店名称
     * @param id
     * @return
     * @throws Exception
     */
    public List<String> getHotelFeatureList(Integer id)throws Exception;
    public String getHotelName(Integer id)throws Exception;
    public List<String> getTradingAreaNameList(Integer id)throws Exception;

    public ItripHotel getItripHotelById(@Param(value = "id") Long id);
}
