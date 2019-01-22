package com.inventory.inventory.task.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskInfo {
    private Integer taskId;

    private String taskStatus;

    private String isValid;

    private Integer sendUserId;

    List<TaskGoodsInfo> taskGoodsInfos;

    private Integer createOperator;

    private Date createTime;

    private Integer updateOperator;

    private Date updateTime;


}