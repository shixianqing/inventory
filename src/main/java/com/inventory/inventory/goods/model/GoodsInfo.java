package com.inventory.inventory.goods.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsInfo {
    private Integer goodsId;

    private String goodsName;

    private String goodsStandard;

    private String goodsAddress;

    private String goodsImgUrl;

    private BigDecimal goodsPrice;

    private Integer createOperator;

    private Date createTime;

    private Integer updateOperator;

    private Date updateTime;

}