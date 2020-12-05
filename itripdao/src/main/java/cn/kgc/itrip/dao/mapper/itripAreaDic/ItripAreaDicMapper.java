package cn.kgc.itrip.dao.mapper.itripAreaDic;

import cn.kgc.itrip.beans.model.ItripAreaDic;
import cn.kgc.itrip.beans.vo.ItripTradeAreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripAreaDicMapper {

    /**
     * 查询热门城市
     * @param isChina
     * @return
     */
    public List<ItripAreaDic> queryHotCity(Integer isChina);

    public List<ItripTradeAreaVo> queryTrading(Integer cityId);
}
