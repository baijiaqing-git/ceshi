package cn.kgc.itrip.biz.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUserLinkUser;
import cn.kgc.itrip.beans.vo.user.ItripModifyUserLinkUserVO;

import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 16:04
 */
public interface IUserInfoService {

    /**
     * 根据UserId,查询联系常用联系人
     * @param token
     * @return
     */
    public ServerResponse queryUserLinkUser(String token);

    /**
     * 添加常用联系人信息
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    /**
     * 修改常用联系人信息
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;

    /**
     * 删除常用联系人信息
     * @param ids
     * @return
     * @throws Exception
     */
    public Integer deleteItripUserLinkUserByIds(Long[] ids)throws Exception;


    /**
     * 查询所有未支付的订单所关联的所有常用联系人
     * @return
     * @throws Exception
     */
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;

}
