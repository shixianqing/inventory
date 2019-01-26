package com.inventory.inventory.store.dao;

import com.inventory.inventory.store.model.StoreInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreInfoMapper {
    int deleteByPrimaryKey(Integer storeId);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(Integer storeId);

    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);

    List<StoreInfo> queryAllStores();
}