package com.inventory.inventory.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable {
    private Integer userId;

    private String userLoginNo;

    private String weixinId;

    private String userName;

    private String userPwd;

    private Integer isValid;

    private Date createTime;

    private Integer updateOperator;

    private Date updateTime;

}