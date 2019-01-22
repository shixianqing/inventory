package com.inventory.inventory.task.dao;

import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.model.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(TaskInfoDto record);

    List<Map> pageQuery(TaskInfoDto taskInfoDto);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(TaskInfoDto record);

    int updateByPrimaryKey(TaskInfo record);

}