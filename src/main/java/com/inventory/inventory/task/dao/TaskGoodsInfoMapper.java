package com.inventory.inventory.task.dao;

import com.inventory.inventory.task.model.TaskGoodsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskGoodsInfoMapper {
    int deleteByPrimaryKey(Integer taskGoodsId);

    int insert(List<TaskGoodsInfo> taskGoodsInfos);

    int insertSelective(TaskGoodsInfo record);

    TaskGoodsInfo selectByPrimaryKey(Integer taskGoodsId);

    int updateByPrimaryKeySelective(TaskGoodsInfo record);

    int updateByPrimaryKey(TaskGoodsInfo record);
}