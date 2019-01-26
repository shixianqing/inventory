package com.inventory.inventory.store.service.impl;

import com.inventory.inventory.store.dao.StoreInfoMapper;
import com.inventory.inventory.store.model.StoreInfo;
import com.inventory.inventory.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2511:28
 * @Description:
 **/
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreInfoMapper storeInfoMapper;

    @Override
    public List<StoreInfo> queryAllStores() {
        return storeInfoMapper.queryAllStores();
    }

    @Override
    public void add(StoreInfo storeInfo) {

    }

    @Override
    public void update(StoreInfo storeInfo) {

    }

    @Override
    public void delete(Integer storeId) {

    }
}
