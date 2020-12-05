package cn.kgc.itrip.auth.service.impl;

import cn.kgc.itrip.auth.service.IUservice;
import cn.kgc.itrip.auth.service.TokenService;
import cn.kgc.itrip.beans.common.Const;
import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.vo.ItripTokenVo;
import cn.kgc.itrip.beans.vo.order.ItripUserVo;
import cn.kgc.itrip.dao.mapper.itripUser.ItripUserMapper;
import cn.kgc.itrip.utils.MD5Util;
import cn.kgc.itrip.utils.RedisApi;
import cn.kgc.itrip.utils.SMSSendUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("iUserService")
@Slf4j
public class UserServiceImpl implements IUservice {

    @Resource
    private ItripUserMapper itripUserMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisApi redisApi;

    /**
     * 用户登录 主业务
     *
     * @param userCode 用户名
     * @param password 密码
     * @return 相关数据
     */
    public ServerResponse login(String userCode, String password, String userAgent) {
        log.info("进入用户业务逻辑层 登录方法......");
        //检查参数的合法性
        if (StringUtils.isBlank(userCode) || StringUtils.isBlank(password)) {
            log.error("在用户名登录是发生参数丢失问题...");
            return ServerResponse.createByErrorMessage("参数丢失参数不存在");
        }
        //验证用户的合法性
        Integer rowCount = itripUserMapper.queryUserCode(userCode);
        if (rowCount <= 0) {
            log.error("用户在登陆时输入了错误的用户名");
            return ServerResponse.createByErrorMessage("用户名错误");
        }
        //校验密码
        String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(password);
        //根据用户名和密码来查询用户，如果没有查到数据则表示，用户的密码不对
        ItripUser itripUser = itripUserMapper.queryUserByUserCodeAndUserPassword(userCode, md5EncodeUtf8);
        if (itripUser == null) {
            log.error(userCode + "在登陆时输错了密码...");
            return ServerResponse.createByErrorMessage("密码不正确");
        }
        //TODO token的生成
        String token = tokenService.generatorToken(userAgent, itripUser);
        //TODO token的存储
        redisApi.setEx(token, Const.RedisConst.SESSION_TIMEOUT, JSONObject.toJSONString(itripUser));
        //组装Vo
        ItripTokenVo itripTokenVo = new ItripTokenVo();
        itripTokenVo.setGenTime(System.currentTimeMillis());
        itripTokenVo.setExpTime(System.currentTimeMillis() + Const.RedisConst.SESSION_TIMEOUT * 1000);
        itripTokenVo.setToken(token);
        return ServerResponse.createBySuccess(itripTokenVo);
    }

    /**
     * 用户退出
     *
     * @param token
     * @return
     */
    public ServerResponse logout(String token) {
        log.info("用户退出");
        if (!redisApi.exists(token)) {
            return ServerResponse.createByErrorMessage("token无效或已过期");
        }
        redisApi.del(token);
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    public ServerResponse insertUser(ItripUserVo itripUserVo,Integer type){
        if (itripUserVo ==null){
            return ServerResponse.createByErrorMessage("用户信息丢失，无法完成注册");
        }
        if (!ckUser(itripUserVo.getUserCode()).isSuccess()) {

        }
        //创建用户对象
        ItripUser itripUser =new ItripUser();
        //拷贝属性
        BeanUtils.copyProperties(itripUserVo,itripUser);
        itripUser.setActivated(0);
        itripUser.setUserType(0);
        itripUser.setUserPassword(MD5Util.MD5EncodeUtf8(itripUser.getUserPassword()));

        try {
            //插入用户并获取到id
            itripUserMapper.insertItripUser(itripUser);
            //用户新增完成
            itripUserMapper.updataUserFlatId(itripUser.getId().intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo 发送各种验证码,以及验证码存储
        switch (type){
            case 1:
                //手机注册
                int num=(int)(Math.random()*8999)+1000;
                String []str={num+"","5"};
                SMSSendUtil.sendSms(itripUserVo.getUserCode(),"1",str);
                redisApi.setEx(itripUserVo.getUserCode(),5*60,num+"");
                break;
            case 2:
                //邮箱注册
                break;
        }
        return ServerResponse.createBySuccessMessage("用户注册完成，请留意验证码");
    }
    public ServerResponse ckUser(String name){
        if (StringUtils.isBlank(name)){
            return ServerResponse.createBySuccessMessage("参数丢失......");
        }
        ItripUser itripUser=itripUserMapper.queryUserByUserCode(name);
        if (itripUser == null){
            return ServerResponse.createBySuccessMessage("可以使用");
        }
        if (itripUser.getActivated()==0){
            //当前的用户名是未激活状态
            //验证redis是否存在
            if (redisApi.exists(name)){
                //当前用户尚处于待激活状态 不可使用
                return ServerResponse.createByErrorMessage("用户名不可用");
            }else{
                //todo 调用dao层删除当前用户，因为已经过了验证的时间
                itripUserMapper.deleteUserByUserCode(name);
                return ServerResponse.createBySuccessMessage("用户名可用");
        }
    }else if(itripUser.getActivated()==1) {
            //当前用户是已激活状态
            return ServerResponse.createByErrorMessage("当前用户名不可用");
        }
        return null;
    }

    @Override
    public ServerResponse checkSecurityCode(String userCode,String code){
        if(redisApi.exists(userCode)){
            String codes=redisApi.get(userCode);
            if (StringUtils.equals(codes,code)){
                //todo 校验成功,更新用户状态
                itripUserMapper.updateUserActive(userCode);
                return ServerResponse.createBySuccess();
            }else {
                return ServerResponse.createByErrorMessage("验证码错误");
            }
        }else{
            return ServerResponse.createByErrorMessage("验证码超时");
        }
    }
}
