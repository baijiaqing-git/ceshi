package cn.kgc.itrip.auth.controller;

import cn.kgc.itrip.auth.service.IUservice;
import cn.kgc.itrip.beans.common.ServerResponse;
import cn.kgc.itrip.beans.vo.order.ItripUserVo;
import com.mysql.fabric.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author 白佳庆
 * @version 1.0
 * @date 2020/10/28 20:19
 */
@Controller
@RequestMapping(value = "/api")
public class UserController {

    @Resource
    private IUservice iUservice;

    /**
     * 使用手机注册
     * @param itripUserVo
     * @return
     */
    @RequestMapping(value = "/registerbyphone",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse validatephone(@RequestBody ItripUserVo itripUserVo){
        return iUservice.insertUser(itripUserVo,1);
    }

    /**
     * 使用邮箱注册
     * @param itripUserVo
     * @return
     */
    @RequestMapping(value = "/doregister",method =RequestMethod.POST)
    @ResponseBody
    public ServerResponse doregister(@RequestBody ItripUserVo itripUserVo){
        return iUservice.insertUser(itripUserVo,2);
    }

    /**
     * 用户名验证
     * @param name
     * @return
     */
    @RequestMapping("/ckusr")
    @ResponseBody
    public ServerResponse ckUser(String name){
        return iUservice.ckUser(name);
    }

    /**
     * 手机注册用户短信验证
     * @param user
     * @param code
     * @return
     */
    @RequestMapping("/validatephone")
    @ResponseBody
    public ServerResponse validatephone(String user,String code){
        return iUservice.checkSecurityCode(user,code);
    }
}
