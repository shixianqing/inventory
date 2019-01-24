package com.inventory.inventory.user.dao;

import com.inventory.inventory.user.model.UserRoleInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleInfo record);

    int insertSelective(UserRoleInfo record);

    UserRoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleInfo record);

    int updateByPrimaryKey(UserRoleInfo record);
}