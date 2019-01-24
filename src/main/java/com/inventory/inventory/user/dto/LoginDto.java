package com.inventory.inventory.user.dto;

import lombok.Data;

/**
 * @Author:shixianqing
 * @Date:2019/1/2414:49
 * @Description:
 **/
@Data
public class LoginDto {

    private String loginName;//登录名  英文+数字
    private String password;
}
