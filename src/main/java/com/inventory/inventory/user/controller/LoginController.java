package com.inventory.inventory.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.redis.RedisService;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.user.dto.LoginDto;
import com.inventory.inventory.user.dto.RegistryDto;
import com.inventory.inventory.user.model.LoginSession;
import com.inventory.inventory.user.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:shixianqing
 * @Date:2019/1/2213:41
 * @Description:
 **/
@RestController
@RequestMapping(value = "/login", produces = {"application/json;charset=utf-8"})
public class LoginController {

    @Value("${customer.appid}")
    private String appId;

    @Value("${customer.appsecret}")
    private String secret;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginService loginService;

    @Value("${spring.expire.time}")
    private Long expireTime;


    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/code2Session")
    public MetaRestResponse executeCode2Session(@RequestParam String code){
        LOGGER.info("code----{}，获取用户会话id，会话密钥，开始了............");
        redisService.getMap(code);
        RestTemplate restTemplate = new RestTemplate();
        String o = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid={1}" +
                "&secret={2}&js_code={3}&grant_type=authorization_code", String.class,appId,secret,code);

        if (ObjectUtils.isEmpty(o)){
            throw new BusinessException(ResponseCode.ERROR,"访问微信Code2Session接口失败！");
        }

        Map<String,Object> result = JSONObject.parseObject(o);
        Object errorCode = result.get("errcode");
        if (!ObjectUtils.isEmpty(errorCode) && !"0".equals(errorCode)){
            throw new BusinessException(Integer.parseInt(errorCode.toString()),result.get("errmsg").toString());
        }

        String uuid = UUID.randomUUID().toString();
        redisService.setMap(uuid,result,1000*60*60*24*30L);
        LOGGER.info("获取用户会话id，会话密钥，结束了...........");
        return MetaRestResponse.success(ResponseCode.SUCCESS,uuid);

    }


    @PostMapping("/registry")
    public MetaRestResponse registry(@RequestBody RegistryDto registryDto){
        LOGGER.info("进入registry方法了.........");
        loginService.registry(registryDto);
        LOGGER.info("退出registry方法了.........");
        return MetaRestResponse.success(ResponseCode.SUCCESS,"注册成功");
    }


    @PostMapping("/login")
    public MetaRestResponse login(@RequestBody LoginDto loginDto){
        LOGGER.info("进入login方法了.........");
        LoginSession userInfo = loginService.login(loginDto);
        String uuid = UUID.randomUUID().toString();
        Map response = new HashMap();
        response.put("token",uuid);
        response.put("roleId",userInfo.getRoleId());
        response.put("userName",userInfo.getUserName());
        redisService.setT(uuid,userInfo,expireTime);
        LOGGER.info("退出login方法了.........token：{}", uuid);
        return MetaRestResponse.success(ResponseCode.SUCCESS,response);
    }
}
