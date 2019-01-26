package com.inventory.inventory.task.vo;

import lombok.Data;

/**
 * @Author:shixianqing
 * @Date:2019/1/2515:41
 * @Description:
 **/
@Data
public class TaskGoodsVo {

    private Integer goodsId;

    private String goodsName;//商品名称

    private Integer goodsNum;//商品数量

    private String goodsUnit;//商品单位
}
