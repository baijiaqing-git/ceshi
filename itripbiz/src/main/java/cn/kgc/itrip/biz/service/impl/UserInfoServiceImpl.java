package cn.kgc.itrip.biz.service.impl;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.model.ItripUserLinkUser;
import cn.kgc.itrip.beans.vo.user.ItripModifyUserLinkUserVO;
import cn.kgc.itrip.biz.service.IUserInfoService;
import cn.kgc.itrip.dao.mapper.itripOrderLinkUser.ItripOrderLinkUserMapper;
import cn.kgc.itrip.dao.mapper.itripUserLinkUser.ItripUserLinkUserMapper;
import cn.kgc.itrip.utils.RedisApi;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 16:04
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
   @Resource
   private ItripUserLinkUserMapper itripUserLinkUserMapper;

    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

   @Resource
   private RedisApi redisApi;

    /**
     * 查询常用联系人
     * @param token
     * @return
     */
    @Override
    public ServerResponse queryUserLinkUser(String token) {
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("用户令牌丢失");
        }
        if (!redisApi.exists(token)){
            return ServerResponse.createByErrorMessage("用户令牌过期");
        }
        return ServerResponse.createBySuccess(itripUserLinkUserMapper.queryLinkUserById
                (JSONObject.parseObject(redisApi.get(token), ItripUser.class).getId().intValue()));
    }

    /**
     * 新增常用联系人
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    @Override
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception{
        itripUserLinkUser.setCreationDate(new Date());
        return itripUserLinkUserMapper.insertItripUserLinkUser(itripUserLinkUser);
    }

    /**
     * 修改常用联系人信息
     * @param itripUserLinkUser
     * @return
     * @throws Exception
     */
    @Override
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception{
        itripUserLinkUser.setModifyDate(new Date());
        return itripUserLinkUserMapper.updateItripUserLinkUser(itripUserLinkUser);
    }

    /**
     * 删除常用联系人信息
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteItripUserLinkUserByIds(Long[] ids)throws Exception{
        return itripUserLinkUserMapper.deleteItripUserLinkUserByIds(ids);
    }

    @Override
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception {
        return itripOrderLinkUserMapper.getItripOrderLinkUserIdsByOrder();
    }

}
