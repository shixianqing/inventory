package com.inventory.inventory.task.dto;

import com.inventory.inventory.task.model.TaskGoodsInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2118:24
 * @Description:
 **/
@Data
public class TaskInfoDto {

    private Integer taskId;

    private String taskStatus;

    List<TaskGoodsInfo> taskGoodsInfos;

    private String isValid;

    private Integer sendUserId;

    private Integer createOperator;//微信id

    private Date createTime;

    private Integer updateOperator;

    private Date updateTime;

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}
