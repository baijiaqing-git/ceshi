package cn.kgc.itrip.dao.mapper.itripOrderLinkUser;

import cn.kgc.itrip.beans.model.ItripOrderLinkUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripOrderLinkUserMapper {

    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;

}

