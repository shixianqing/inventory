package com.inventory.inventory.store.service;

import com.inventory.inventory.store.model.StoreInfo;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2511:22
 * @Description:
 **/
public interface StoreService {

    List<StoreInfo> queryAllStores();

    void add(StoreInfo storeInfo);

    void update(StoreInfo storeInfo);

    void delete(Integer storeId);

}
