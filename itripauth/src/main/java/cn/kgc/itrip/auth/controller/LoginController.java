package cn.kgc.itrip.auth.controller;

import cn.kgc.itrip.auth.service.IUservice;
import cn.kgc.itrip.beans.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Resource
    private IUservice iUservice;

    @RequestMapping(value = "dologin",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse doLogin(@RequestParam("name")String name,
                                  @RequestParam("password")String password,
                                  HttpServletRequest request){
        return iUservice.login(name,password,request.getHeader("User-Agent"));
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse dologout(HttpServletRequest request){
        return iUservice.logout(request.getHeader("token"));
    }
}
