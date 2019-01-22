package com.inventory.inventory.task.service.impl;

import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.task.dao.TaskGoodsInfoMapper;
import com.inventory.inventory.task.dao.TaskInfoMapper;
import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.model.TaskGoodsInfo;
import com.inventory.inventory.task.model.TaskInfo;
import com.inventory.inventory.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2019/1/2119:55
 * @Description:
 **/
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Autowired
    private TaskGoodsInfoMapper taskGoodsInfoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public void addTask(TaskInfoDto taskInfoDto) {
        if (ObjectUtils.isEmpty(taskInfoDto)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"订货信息不能全为空！");
        }
        taskInfoDto.setIsValid("1");//有效
        taskInfoDto.setTaskStatus("0");//待分配
        taskInfoDto.setCreateTime(new Date());
        try {
            taskInfoMapper.insert(taskInfoDto);
            Integer taskId = taskInfoDto.getTaskId();
            for (TaskGoodsInfo taskGoodsInfo:taskInfoDto.getTaskGoodsInfos()){
                taskGoodsInfo.setTaskId(taskId);
            }
            taskGoodsInfoMapper.insert(taskInfoDto.getTaskGoodsInfos());

        } catch (Exception e){
            LOGGER.error("新增任务信息失败！！！{}",e.getCause());
            throw new BusinessException(ResponseCode.INSERT_FAIL_CODE,"订货信息新增失败！");
        }
    }

    @Override
    public List<Map> pageQuery(TaskInfoDto taskInfoDto) {
        return  taskInfoMapper.pageQuery(taskInfoDto);
    }

    /**
     * 作废当前任务
     * @param taskInfoDto
     */
    @Override
    public void invalidTask(TaskInfoDto taskInfoDto) {

        if (ObjectUtils.isEmpty(taskInfoDto.getTaskId())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"作废当前任务，任务id不能为空！");
        }

        taskInfoDto.setIsValid("0");
        taskInfoDto.setUpdateTime(new Date());
        try{
            taskInfoMapper.updateByPrimaryKeySelective(taskInfoDto);
        }catch (Exception e){
            LOGGER.error("作废当前任务失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"作废当前任务失败！");
        }

    }

    /**
     * 管理员分配任务
     * @param taskInfoDto
     */
    @Override
    public void assignTask(TaskInfoDto taskInfoDto) {
        if (ObjectUtils.isEmpty(taskInfoDto.getSendUserId())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"分配任务时，送货员id不能为空！");
        }

        taskInfoDto.setUpdateTime(new Date());
        try{
            taskInfoMapper.updateByPrimaryKeySelective(taskInfoDto);
        }catch (Exception e){
            LOGGER.error("分配任务失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"分配任务失败！");
        }
    }
}
