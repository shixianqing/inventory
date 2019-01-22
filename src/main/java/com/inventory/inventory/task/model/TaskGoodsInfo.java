package com.inventory.inventory.task.model;

import lombok.Data;

@Data
public class TaskGoodsInfo {
    private Integer taskGoodsId;

    private Integer taskId;

    private Integer storeId;

    private Integer goodsId;

    private Integer goodsNum;

    private String goodsUnit;

}