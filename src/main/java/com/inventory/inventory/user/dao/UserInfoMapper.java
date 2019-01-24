package com.inventory.inventory.user.dao;

import com.inventory.inventory.user.dto.RegistryDto;
import com.inventory.inventory.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String loginName);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int registry(RegistryDto registryDto);
}