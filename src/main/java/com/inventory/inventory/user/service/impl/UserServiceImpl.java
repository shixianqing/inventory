package com.inventory.inventory.user.service.impl;

import com.inventory.inventory.user.dao.UserInfoMapper;
import com.inventory.inventory.user.model.Role;
import com.inventory.inventory.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2510:48
 * @Description:
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<Role> queryAllRoles() {
        return userInfoMapper.queryAllRoles();
    }

    @Override
    public List<UserInfo> getDeliverGoodsPerson() {
        return userInfoMapper.getDeliverGoodsPerson();
    }
}
