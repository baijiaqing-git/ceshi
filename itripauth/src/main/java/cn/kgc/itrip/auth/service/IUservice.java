package cn.kgc.itrip.auth.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.order.ItripUserVo;


public interface IUservice {
    /**
     * 登录
     */

    public ServerResponse login(String userCode, String password, String userAgent) ;

    /**
     * 注销
     */
    public ServerResponse logout(String token);

    public ServerResponse insertUser(ItripUserVo itripUserVo, Integer type) ;
    public ServerResponse ckUser(String name);
    public ServerResponse checkSecurityCode(String userCode,String code);

}

