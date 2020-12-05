package cn.kgc.itrip.dao.mapper.itripProductStore;

import cn.kgc.itrip.beans.model.ItripProductStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripProductStoreMapper {

    /**
     * 根据房间id来查询房间剩余库存
     * @param roomId
     * @return
     */
    public Integer queryStoreByRoomId(Integer roomId);
}
