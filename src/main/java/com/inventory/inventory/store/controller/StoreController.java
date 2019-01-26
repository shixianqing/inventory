package com.inventory.inventory.store.controller;

import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.store.model.StoreInfo;
import com.inventory.inventory.store.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2511:17
 * @Description:商户管理
 **/
@RestController
@RequestMapping(value = "/store",produces = {"application/json;charset=utf-8"})
public class StoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @GetMapping("/query")
    public MetaRestResponse queryAllStores(){

        LOGGER.info("进入【queryAllStores】方法了..........");
        List<StoreInfo> storeInfos = storeService.queryAllStores();
        LOGGER.info("退出【queryAllStores】方法了..........结果为：{}", JSONObject.toJSONString(storeInfos,true));

        return MetaRestResponse.success(ResponseCode.SUCCESS,storeInfos);
    }
}
