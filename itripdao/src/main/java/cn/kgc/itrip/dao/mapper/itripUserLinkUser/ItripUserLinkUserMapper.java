package cn.kgc.itrip.dao.mapper.itripUserLinkUser;

import cn.kgc.itrip.beans.model.ItripUserLinkUser;
import cn.kgc.itrip.beans.vo.user.ItripModifyUserLinkUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItripUserLinkUserMapper {

    /**
     * 查询常用联系人
     * @param userId
     * @return
     */
    public List<ItripUserLinkUser>queryLinkUserById(Integer userId);

    /**
     * 新增常用联系人
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    public Integer insertItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    /**
     * 修改常用联系人
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    public Integer updateItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    /**
     * 删除常用联系人
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer deleteItripUserLinkUserByIds(@Param(value = "ids") Long[] ids)throws Exception;

    public Integer modifyuserlinkuser(ItripModifyUserLinkUserVO itripModifyUserLinkUserVO)throws Exception;

}
