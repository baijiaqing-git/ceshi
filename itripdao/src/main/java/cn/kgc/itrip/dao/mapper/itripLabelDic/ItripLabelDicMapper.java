package cn.kgc.itrip.dao.mapper.itripLabelDic;

import cn.kgc.itrip.beans.model.ItripHotelFeature;
import cn.kgc.itrip.beans.model.ItripLabelDic;
import cn.kgc.itrip.beans.vo.ItripHotrlFeatureVo;
import cn.kgc.itrip.beans.vo.ItripTravelTypeVo;
import cn.kgc.itrip.beans.vo.hotelroom.SearchHotelroombedVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripLabelDicMapper {


    /**
     * 查询酒店特色
     * @return
     */
    public List<ItripHotelFeature> getHotelFeature();

    /**
     * 查询床型列表
     * @return
     * @throws Exception
     */
    public List<SearchHotelroombedVo> queryhotelroombed()throws Exception;

    /**
     * 查询出游类型列表
     * @return
     * @throws Exception
     */
    public List<ItripTravelTypeVo>gettraveltype()throws Exception;
}
