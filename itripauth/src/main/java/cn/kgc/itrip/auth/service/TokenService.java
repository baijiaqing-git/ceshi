package cn.kgc.itrip.auth.service;

import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.model.ItripUser;

public interface TokenService {
    public String generatorToken(String userAgent, ItripUser itripUser);

    public ServerResponse retoken(String userAgent, String token) ;
}
