package com.inventory.inventory.user.dao;

import com.inventory.inventory.user.model.UserStoreInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserStoreInfoMapper {
    int deleteByPrimaryKey(Integer userStoreId);

    int insert(UserStoreInfo record);

    int insertSelective(UserStoreInfo record);

    UserStoreInfo selectByPrimaryKey(Integer userStoreId);

    int updateByPrimaryKeySelective(UserStoreInfo record);

    int updateByPrimaryKey(UserStoreInfo record);
}