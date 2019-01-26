package com.inventory.inventory.task.dao;
import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.model.TaskInfo;
import com.inventory.inventory.task.vo.TaskInfoVo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(TaskInfoDto record);

    List<TaskInfoVo> pageQuery(TaskInfoDto taskInfoDto);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(TaskInfoDto record);

    int updateByPrimaryKey(TaskInfo record);

    int batchUpdate(List<TaskInfoDto> taskInfoDtos);

}