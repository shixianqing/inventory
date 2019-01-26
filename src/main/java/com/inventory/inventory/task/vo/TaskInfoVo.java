package com.inventory.inventory.task.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2515:36
 * @Description:
 **/
@Data
public class TaskInfoVo {

    private Integer taskId;

    private String taskStatus;

    private String isValid;

    private Integer sendUserId;

    private String sendUserName;//送货员名称

    private Integer createOperator;

    private String createOperatorName;//创建任务人名称

    private Date createTime;

    private Integer updateOperator;

    private Date updateTime;

    private String storeName;//商户名称

    private String storeAddress;//商户地址

    private String leaderName;//商户负责人名称

    private String leaderPhoneNo;//负责人手机号码

    private List<TaskGoodsVo> taskGoodsVos;
}
