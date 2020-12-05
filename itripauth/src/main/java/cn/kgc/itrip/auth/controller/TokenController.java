package cn.kgc.itrip.auth.controller;

import cn.kgc.itrip.auth.service.TokenService;
import cn.kgc.itrip.beans.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 19:44
 */
@Controller
@RequestMapping(value = "/api")
public class TokenController {

    @Resource
    private TokenService tokenService;
    @RequestMapping(value = "/retoken",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse retoken(HttpServletRequest request){
        return tokenService.retoken(request.getHeader("user-agent"),request.getHeader("token"));
    }
}
