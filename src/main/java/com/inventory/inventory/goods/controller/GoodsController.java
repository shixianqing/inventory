package com.inventory.inventory.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.common.util.FtpUtil;
import com.inventory.inventory.goods.model.GoodsInfo;
import com.inventory.inventory.goods.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 商品信息管理
 */
@RestController
@RequestMapping(value = "/goods",produces = {"application/json;charset=utf-8"})
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FtpUtil ftpUtil;

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


    @GetMapping("/image/show")
    public String showImage(@RequestParam String fileName){
        LOGGER.info("文件名为：{}，进入到showImage了",fileName);
        String base64 = ftpUtil.readFileToBase64(fileName);
        StringBuilder stringBuilder = new StringBuilder("data:image/png;base64,");
        stringBuilder.append(base64);
        base64 = stringBuilder.toString();
        LOGGER.info("文件名为：{}的图片对应的base64流为：{}",fileName,base64);
        return base64;
    }

    @GetMapping("/image/upload")
    public void uploadFile(MultipartHttpServletRequest request) throws IOException {
        List<MultipartFile> multipartFiles = request.getFiles("file");
        for (MultipartFile multipartFile:multipartFiles){
            String originName = multipartFile.getOriginalFilename();
            LOGGER.info("上传文件：{}",originName);
            ftpUtil.uploadFile(multipartFile.getInputStream(),originName);
        }
    }
}
