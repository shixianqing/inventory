package com.inventory.inventory.goods.service.impl;

import com.inventory.inventory.goods.dao.GoodsInfoMapper;
import com.inventory.inventory.goods.model.GoodsInfo;
import com.inventory.inventory.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public List<GoodsInfo> queryGoods() {
        return goodsInfoMapper.dynamicQuery();
    }
}
