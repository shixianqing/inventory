package com.inventory.inventory.task.service.impl;

import com.inventory.inventory.common.enums.Role;
import com.inventory.inventory.common.enums.TaskStatus;
import com.inventory.inventory.common.enums.TaskValid;
import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.redis.RedisService;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.task.dao.TaskGoodsInfoMapper;
import com.inventory.inventory.task.dao.TaskInfoMapper;
import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.model.TaskGoodsInfo;
import com.inventory.inventory.task.service.TaskService;
import com.inventory.inventory.task.vo.TaskInfoVo;
import com.inventory.inventory.user.model.LoginSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private RedisService redisService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public void addTask(TaskInfoDto taskInfoDto,String token) {
        if (ObjectUtils.isEmpty(taskInfoDto)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"订货信息不能全为空！");
        }
        taskInfoDto.setIsValid(TaskValid.VALID.getIsValid());//有效
        taskInfoDto.setTaskStatus(TaskStatus.WAITING.getStatus());//待分配
        taskInfoDto.setCreateTime(new Date());
        LoginSession userInfo = redisService.getT(token);
        taskInfoDto.setCreateOperator(userInfo.getUserId());
        try {
            taskInfoMapper.insert(taskInfoDto);
            Integer taskId = taskInfoDto.getTaskId();
            for (TaskGoodsInfo taskGoodsInfo:taskInfoDto.getTaskGoodsInfos()){
                taskGoodsInfo.setTaskId(taskId);
                taskGoodsInfo.setStoreId(userInfo.getStoreId());
            }
            taskGoodsInfoMapper.insert(taskInfoDto.getTaskGoodsInfos());

        } catch (Exception e){
            LOGGER.error("新增任务信息失败！！！{}",e.getCause());
            throw new BusinessException(ResponseCode.INSERT_FAIL_CODE,"订货信息新增失败！");
        }
    }

    /**
     *  查询任务
     * @param taskInfoDto
     * @return
     */
    @Override
    public List<TaskInfoVo> pageQuery(TaskInfoDto taskInfoDto,String token) {
        return null;
    }

    /**
     * 作废当前任务
     * @param taskInfoDto
     */
    @Override
    public void invalidTask(TaskInfoDto taskInfoDto,String token) {

        if (ObjectUtils.isEmpty(taskInfoDto.getTaskId())){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"作废当前任务，任务id不能为空！");
        }
        LoginSession userInfo = redisService.getT(token);
        taskInfoDto.setIsValid(TaskValid.INVALID.getIsValid());
        taskInfoDto.setUpdateTime(new Date());
        taskInfoDto.setUpdateOperator(userInfo.getUserId());
        try{
            taskInfoMapper.updateByPrimaryKeySelective(taskInfoDto);
        }catch (Exception e){
            LOGGER.error("作废当前任务失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"作废当前任务失败！");
        }

    }

    /**
     * 管理员分配任务
     * @param taskInfoDtos
     */
    @Override
    public void assignTask(List<TaskInfoDto> taskInfoDtos,String token) {
        if (ObjectUtils.isEmpty(taskInfoDtos)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"请求参数不能为空！");
        }
        LoginSession userInfo = redisService.getT(token);

        for (TaskInfoDto taskInfoDto:taskInfoDtos){
            if (ObjectUtils.isEmpty(taskInfoDto.getSendUserId())){
                throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"分配任务时，送货员id不能为空！");
            }
            if (ObjectUtils.isEmpty(taskInfoDto.getTaskId())){
                throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"分配任务时，任务id不能为空！");
            }
            taskInfoDto.setUpdateTime(new Date());
            taskInfoDto.setUpdateOperator(userInfo.getUserId());
        }

        try{
            taskInfoMapper.batchUpdate(taskInfoDtos);
        }catch (Exception e){
            LOGGER.error("分配任务失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"分配任务失败！");
        }
    }

    @Override
    public void receiveTask(List<TaskInfoDto> taskInfoDtos, String token) {
        if (ObjectUtils.isEmpty(taskInfoDtos)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"请求参数不能为空！");
        }
        LoginSession userInfo = redisService.getT(token);

        for (TaskInfoDto taskInfoDto:taskInfoDtos){
            if (ObjectUtils.isEmpty(taskInfoDto.getTaskId())){
                throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"任务id不能为空！");
            }
            taskInfoDto.setUpdateTime(new Date());
            taskInfoDto.setTaskStatus(TaskStatus.PROCESSING.getStatus());
            taskInfoDto.setUpdateOperator(userInfo.getUserId());
        }

        try{
            taskInfoMapper.batchUpdate(taskInfoDtos);
        }catch (Exception e){
            LOGGER.error("接收任务失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"接收任务失败！");
        }
    }

    @Override
    public List<TaskInfoVo> queryTasks(TaskInfoDto taskInfoDto,String token) {
        LoginSession userInfo = redisService.getT(token);
        Integer roleId = userInfo.getRoleId();
        if (Role.DELIVERY_MAIN.getType()==roleId){
            taskInfoDto.setSendUserId(userInfo.getUserId());
        } else if (Role.DEALER.getType() == roleId){
            taskInfoDto.setCreateOperator(userInfo.getUserId());
        }
        return  taskInfoMapper.pageQuery(taskInfoDto);
    }

    @Override
    public void compeleteTask(List<TaskInfoDto> taskInfoDtos, String token) {
        if (ObjectUtils.isEmpty(taskInfoDtos)){
            throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"请求参数不能为空！");
        }
        LoginSession userInfo = redisService.getT(token);

        for (TaskInfoDto taskInfoDto:taskInfoDtos){
            if (ObjectUtils.isEmpty(taskInfoDto.getTaskId())){
                throw new BusinessException(ResponseCode.PARAM_EMPTY_CODE,"任务id不能为空！");
            }
            taskInfoDto.setUpdateTime(new Date());
            taskInfoDto.setTaskStatus(TaskStatus.COMPLETED.getStatus());
            taskInfoDto.setUpdateOperator(userInfo.getUserId());
        }

        try{
            taskInfoMapper.batchUpdate(taskInfoDtos);
        }catch (Exception e){
            LOGGER.error("任务完成，更新失败！{}",e.getCause());
            throw new BusinessException(ResponseCode.UPDATE_FAIL_CODE,"任务完成，更新失败！");
        }
    }

}
