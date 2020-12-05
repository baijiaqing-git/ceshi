package cn.kgc.itrip.biz.controller;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.model.ItripUserLinkUser;
import cn.kgc.itrip.beans.vo.user.ItripAddUserLinkUserVO;
import cn.kgc.itrip.beans.vo.user.ItripModifyUserLinkUserVO;
import cn.kgc.itrip.biz.service.IUserInfoService;
import cn.kgc.itrip.utils.EmptyUtils;
import cn.kgc.itrip.utils.RedisApi;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/30 16:04
 */
@Controller
@RequestMapping(value = "/api/userinfo")
@Slf4j
public class UserInfoController {
    @Resource
    private IUserInfoService iUserInfoService;

    @Resource
    private RedisApi redisApi;

    /**
     * 根据UserId,查询联系常用联系人
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("queryuserlinkuser")
    @ResponseBody
    public ServerResponse queryuserlinkuser(HttpServletRequest httpServletRequest) {
        return iUserInfoService.queryUserLinkUser(httpServletRequest.getHeader("token"));
    }

    /**
     * 添加常用联系人信息
     *
     * @param itripAddUserLinkUserVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ServerResponse addUserLinkUser(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO, HttpServletRequest request) {
        String tokenString = request.getHeader("token");
        log.debug("token name is from header : " + tokenString);
        ItripUser currentUser = JSONObject.parseObject(redisApi.get(tokenString), ItripUser.class);
        if (null != currentUser && null != itripAddUserLinkUserVO) {
            ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
            itripUserLinkUser.setLinkUserName(itripAddUserLinkUserVO.getLinkUserName());
            itripUserLinkUser.setLinkIdCardType(itripAddUserLinkUserVO.getLinkIdCardType());
            itripUserLinkUser.setLinkIdCard(itripAddUserLinkUserVO.getLinkIdCard());
            itripUserLinkUser.setUserId(Integer.valueOf(currentUser.getId().toString()));
            itripUserLinkUser.setLinkPhone(itripAddUserLinkUserVO.getLinkPhone());
            itripUserLinkUser.setCreatedBy(currentUser.getId());
            itripUserLinkUser.setCreationDate(new Date(System.currentTimeMillis()));
            try {
                iUserInfoService.addItripUserLinkUser(itripUserLinkUser);
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("新增常用联系人失败");
            }
            return ServerResponse.createBySuccess("新增常用联系人成功");
        } else if (null != currentUser && null == itripAddUserLinkUserVO) {
            return ServerResponse.createByErrorMessage("不能提交空，请填写常用联系人信息");
        } else {
            return ServerResponse.createBySuccess("token失效，请重新登录", "100000");
        }
    }

    /**
     * 修改常用联系人
     * @param itripModifyUserLinkUserVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyuserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateUserLinkUser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO, HttpServletRequest request) {
        String tokenString  = request.getHeader("token");
        log.debug("token name is from header : " + tokenString);
        ItripUser currentUser = JSONObject.parseObject(redisApi.get(tokenString),ItripUser.class);
        if (null != currentUser && null != itripModifyUserLinkUserVO) {
            ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
            itripUserLinkUser.setId(itripModifyUserLinkUserVO.getId());
            itripUserLinkUser.setLinkUserName(itripModifyUserLinkUserVO.getLinkUserName());
            itripUserLinkUser.setLinkIdCardType(itripModifyUserLinkUserVO.getLinkIdCardType());
            itripUserLinkUser.setLinkIdCard(itripModifyUserLinkUserVO.getLinkIdCard());
            itripUserLinkUser.setUserId(Integer.valueOf(currentUser.getId().toString()));
            itripUserLinkUser.setLinkPhone(itripModifyUserLinkUserVO.getLinkPhone());
            itripUserLinkUser.setModifiedBy(currentUser.getId());
            itripUserLinkUser.setModifyDate(new Date(System.currentTimeMillis()));
            try {
                iUserInfoService.modifyItripUserLinkUser(itripUserLinkUser);
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.createBySuccess("修改常用联系人失败", "100421");
            }
            return ServerResponse.createBySuccess("修改常用联系人成功");
        } else if (null != currentUser && null == itripModifyUserLinkUserVO) {
            return ServerResponse.createByErrorMessage("不能提交空，请填写常用联系人信息");
        } else {
            return ServerResponse.createByErrorMessage("token失效，请重新登录");
        }
    }

    /**
     * 删除常用联系人接口
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value="/deluserlinkuser",method=RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public ServerResponse delUserLinkUser(Long[] ids, HttpServletRequest request) {
        String tokenString  = request.getHeader("token");
        log.debug("token name is from header : " + tokenString);
        ItripUser currentUser = JSONObject.parseObject(redisApi.get(tokenString),ItripUser.class);
        List<Long> idsList = new ArrayList<Long>();
        if(null != currentUser && EmptyUtils.isNotEmpty(ids)){
            try {
                List<Long> linkUserIds = iUserInfoService.getItripOrderLinkUserIdsByOrder();
                Collections.addAll(idsList, ids);
                idsList.retainAll(linkUserIds);
                if(idsList.size() > 0)
                {
                    return ServerResponse.createBySuccess("所选的常用联系人中有与某条待支付的订单关联的项，无法删除","100431");
                }else{
                    iUserInfoService.deleteItripUserLinkUserByIds(ids);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.createBySuccess("删除常用联系人失败","100432");
            }
            return ServerResponse.createBySuccess("删除常用联系人成功");
        }else if(null != currentUser && EmptyUtils.isEmpty(ids)){
            return ServerResponse.createBySuccess("请选择要删除的常用联系人","100433");
        }else{
            return ServerResponse.createByErrorMessage("token失效，请重新登录");
        }
    }

}
