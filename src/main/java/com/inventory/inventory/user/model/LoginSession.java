package com.inventory.inventory.user.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:shixianqing
 * @Date:2019/1/2420:38
 * @Description:
 **/
@Data
public class LoginSession implements Serializable {

    private Integer userId;

    private String userLoginNo;

    private String userName;

    private String userPwd;

    private Integer roleId;

    private Integer storeId;
}
