package com.inventory.inventory.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.https.HttpsClientRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2019/1/2213:41
 * @Description:
 **/
@RestController
@RequestMapping(value = "/login", produces = {"application/json;charset=utf-8"})
public class LoginController {

    @GetMapping("/code2Session")
    public void executeCode2Session(@RequestParam String code){
        RestTemplate restTemplate = new RestTemplate();
        String o = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session?appid={1}" +
                "&secret={2}&js_code={3}&grant_type=authorization_code", String.class,"wxf1e771a58fe37666",
                "ae30ece707e62208bc7c8be58bec55c8",code);
        Map<String,Object> result = JSONObject.parseObject(o);
        System.out.println(result);
    }
}
