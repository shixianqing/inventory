package com.inventory.inventory.user.service.impl;

import com.inventory.inventory.user.model.Role;
import com.inventory.inventory.user.model.UserInfo;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2510:48
 * @Description:
 **/
public interface UserService {
    List<Role> queryAllRoles();

    List<UserInfo> getDeliverGoodsPerson();
}
