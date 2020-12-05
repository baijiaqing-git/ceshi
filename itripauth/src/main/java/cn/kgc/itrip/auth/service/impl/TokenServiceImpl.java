package cn.kgc.itrip.auth.service.impl;

import cn.kgc.itrip.auth.service.TokenService;
import cn.kgc.itrip.beans.common.Const;
import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUser;
import cn.kgc.itrip.beans.vo.ItripTokenVo;
import cn.kgc.itrip.utils.MD5Util;
import cn.kgc.itrip.utils.RedisApi;
import cn.kgc.itrip.utils.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("tokenService")
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Override
    public String generatorToken(String userAgent, ItripUser itripUser) {
        log.info("生成token....");
        if (StringUtils.isBlank(userAgent)) {
            log.error("用户表示丢失");
        }
        StringBuffer sb = new StringBuffer();
        sb.append("token:");
        //识别设备标识
        if (UserAgentUtil.CheckAgent(userAgent)) {
            //移动设备
            sb.append("MOBILE");
        } else {
            //非移动设备
            sb.append("PC-");
        }
        sb.append(MD5Util.MD5EncodeUtf8(itripUser.getUserCode()) + "-");
        sb.append(itripUser.getId() + "-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //生成时间戳
        sb.append(simpleDateFormat.format(new Date()) + "-");
        //截取六位用户标识码
        sb.append(StringUtils.substring(MD5Util.MD5EncodeUtf8(userAgent), 0, 6));
        return sb.toString();
    }

    @Resource
    private RedisApi redisApi;

    @Override
    public ServerResponse retoken(String userAgent, String token) {
        //判断参数
        if (StringUtils.isBlank(userAgent) || StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("参数丢失....");
        }
        //判断token是否过期
        //获取过期时间
        Long ttl = redisApi.ttl(token);
        if (ttl < -1) {
            return ServerResponse.createByErrorMessage("token无效或已过期");
        }
        //检查过期时间，是否在保护期内
        if (ttl > Const.RedisConst.REPLACEMENT_PROTECTION_TIMEOUT) {
            return ServerResponse.createByErrorMessage("token尚处于保护期内，不予置换");
        }
        String userAgentO = StringUtils.substring(token, StringUtils.lastIndexOf(token, "-") + 1, token.length());
        if (StringUtils.equals(userAgentO, StringUtils.substring(MD5Util.MD5EncodeUtf8(userAgent), 0, 6))) {
            return ServerResponse.createByErrorMessage("用户浏览器发生了更换，请重新登录");
        }
        String newToken = generatorToken(userAgent, JSONObject.parseObject(redisApi.get(token), ItripUser.class));
        //给旧token设置新的过期时间
        redisApi.setEx(token, Const.RedisConst.REPLACEMENT_DELAY, redisApi.get(token));
        redisApi.setEx(newToken, Const.RedisConst.SESSION_TIMEOUT, redisApi.get(token));
        ItripTokenVo itripTokenVo = new ItripTokenVo();
        itripTokenVo.setToken(newToken);
        itripTokenVo.setGenTime(System.currentTimeMillis());
        itripTokenVo.setExpTime(System.currentTimeMillis() + Const.RedisConst.SESSION_TIMEOUT * 1000);
        return ServerResponse.createBySuccess(itripTokenVo);
    }
}

