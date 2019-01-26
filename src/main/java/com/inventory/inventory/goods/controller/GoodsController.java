package com.inventory.inventory.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.goods.model.GoodsInfo;
import com.inventory.inventory.goods.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品信息管理
 */
@RestController
@RequestMapping(value = "/goods",produces = {"application/json;charset=utf-8"})
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    /**
     * 查询所有商品信息
     * @return
     */
    @GetMapping("/query")
    public MetaRestResponse queryGoods(){
        LOGGER.info("开始查询商品信息........");
        List<GoodsInfo> goodsInfos = goodsService.queryGoods();
        LOGGER.info("查询商品信息结束了.........结果集为：{}", JSONObject.toJSONString(goodsInfos,true));
        return MetaRestResponse.success(ResponseCode.SUCCESS,goodsInfos);
    }
}
