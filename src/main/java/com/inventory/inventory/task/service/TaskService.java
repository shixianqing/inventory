package com.inventory.inventory.task.service;

import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.model.TaskInfo;

import java.util.List;
import java.util.Map;

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
    void addTask(TaskInfoDto taskInfoDto);

    /**
     * 分页查询任务
     * 管理员能看所有任务
     * 线下商户只能看自己创建的任务
     * @param taskInfoDto
     * @return
     */
    List<Map> pageQuery(TaskInfoDto taskInfoDto);

    /**
     * 将当前任务作废
     * 线下商户与管理员都能将当前任务作废
     * @param taskInfoDto
     */
    void invalidTask(TaskInfoDto taskInfoDto);


    /**
     * 分配任务
     * @param taskInfoDto
     */
    void assignTask(TaskInfoDto taskInfoDto);
}
