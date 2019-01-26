package com.inventory.inventory.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.user.model.Role;
import com.inventory.inventory.user.model.UserInfo;
import com.inventory.inventory.user.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2510:43
 * @Description:
 **/
@RestController
@RequestMapping(value = "/user", produces = {"application/json;charset=utf-8"})
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 查询所有角色信息
     * @return
     */
    @GetMapping("/role/query")
    public MetaRestResponse getAllRoles(){
        LOGGER.info("进入getAllRoles方法..........");
        List<Role> roles = userService.queryAllRoles();
        LOGGER.info("退出getAllRoles方法..........查询结果为：{}", JSONObject.toJSONString(roles,true));

        return MetaRestResponse.success(ResponseCode.SUCCESS,roles);
    }

    /**
     * 查询角色是送货员的用户
     * 限定条件在sql配置文件中
     * @return
     */
    @GetMapping("/deliver/query")
    public MetaRestResponse getDeliverGoodsPerson(){
        LOGGER.info("进入getDeliverGoodsPerson方法了.......");
        List<UserInfo> userInfos = userService.getDeliverGoodsPerson();
        LOGGER.info("getDeliverGoodsPerson..........查询结果为：{}", JSONObject.toJSONString(userInfos,true));
        return MetaRestResponse.success(ResponseCode.SUCCESS,userInfos);
    }


}
