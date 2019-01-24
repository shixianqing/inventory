package com.inventory.inventory.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author:shixianqing
 * @Date:2019/1/2414:41
 * @Description:
 **/
@Data
public class RegistryDto {

    private Integer userId;

    private String loginName;//登录名  英文+数字

    private String userName;//用户姓名

    private String password;

    private String confirmPassword;

    private Integer roleId;

    private Integer storeId;//商户id

    private Date createTime;
}
