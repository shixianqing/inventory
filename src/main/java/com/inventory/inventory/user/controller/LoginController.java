package com.inventory.inventory.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.https.HttpsClientRequestFactory;
import com.inventory.inventory.common.redis.RedisService;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
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
        redisService.setMap(uuid,result,1000*60*60*24*30);
        LOGGER.info("获取用户会话id，会话密钥，结束了...........");
        return MetaRestResponse.success(ResponseCode.SUCCESS,uuid);

    }
}
