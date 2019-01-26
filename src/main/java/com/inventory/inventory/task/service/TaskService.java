package com.inventory.inventory.task.service;

import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.vo.TaskInfoVo;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2118:34
 * @Description:
 **/
public interface TaskService {

    /**
     * 新增任务
     * @param taskInfoDto
     */
    void addTask(TaskInfoDto taskInfoDto,String token);

    /**
     * 分页查询任务
     * 管理员能看所有任务
     * 线下商户只能看自己创建的任务
     * @param taskInfoDto
     * @return
     */
    List<TaskInfoVo> pageQuery(TaskInfoDto taskInfoDto,String token);

    /**
     * 将当前任务作废
     * 线下商户与管理员都能将当前任务作废
     * @param taskInfoDto
     */
    void invalidTask(TaskInfoDto taskInfoDto,String token);


    /**
     * 分配任务
     * @param taskInfoDtos
     */
    void assignTask(List<TaskInfoDto> taskInfoDtos,String token);

    /**
     * 送货员接收任务
     * @param taskInfoDtos
     * @param token
     */
    void receiveTask(List<TaskInfoDto> taskInfoDtos,String token);

    List<TaskInfoVo> queryTasks(TaskInfoDto taskInfoDto,String token);

    void compeleteTask(List<TaskInfoDto> taskInfoDtos, String token);
}
