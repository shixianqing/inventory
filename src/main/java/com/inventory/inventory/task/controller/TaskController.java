package com.inventory.inventory.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import com.inventory.inventory.task.dto.TaskInfoDto;
import com.inventory.inventory.task.service.TaskService;
import com.inventory.inventory.task.vo.TaskInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:shixianqing
 * @Date:2019/1/2117:45
 * @Description:采购任务控制器
 **/
@RestController
@RequestMapping(value = "/task",produces = {"application/json;charset=utf-8"})
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public MetaRestResponse addTask(@RequestBody TaskInfoDto taskInfoDto, @RequestHeader("token") String token) {
        LOGGER.info("进入【addTask】方法中了.........请求参数为：{}",JSONObject.toJSONString(taskInfoDto,true));
        taskService.addTask(taskInfoDto,token);
        LOGGER.info("退出【addTask】方法了.........");
        return MetaRestResponse.success(ResponseCode.SUCCESS,"缺货订单任务发起成功！");
    }

    /**
     * 任务分配
     * @param taskInfoDtos
     * @param token
     * @return
     */
    @PostMapping("/assign")
    public MetaRestResponse assignTask(@RequestBody List<TaskInfoDto> taskInfoDtos, @RequestHeader("token") String token) {
        LOGGER.info("进入【assignTask】方法中了.........请求参数为：{}",JSONObject.toJSONString(taskInfoDtos,true));
        taskService.assignTask(taskInfoDtos,token);
        LOGGER.info("退出【assignTask】方法了.........");
        return MetaRestResponse.success(ResponseCode.SUCCESS,"任务分配成功！");
    }

    /**
     * 查询任务
     * @param taskInfoDto
     * @param token
     * @return
     */
    @PostMapping("/query")
    public MetaRestResponse queryTasks(@RequestBody TaskInfoDto taskInfoDto, @RequestHeader("token") String token) {
        LOGGER.info("进入【queryTasks】方法中了.........请求参数为：{}", JSONObject.toJSONString(taskInfoDto,true));
        PageHelper.startPage(taskInfoDto.getPageNo(),taskInfoDto.getPageSize());
        List<TaskInfoVo> taskInfoVos = taskService.queryTasks(taskInfoDto,token);
        LOGGER.info("退出【queryTasks】方法了.........结果集为：{}",JSONObject.toJSONString(taskInfoVos,true));
        return MetaRestResponse.success(ResponseCode.SUCCESS,new PageInfo(taskInfoVos));
    }

    /**
     * 送货员接收任务
     * 批量处理
     * @param taskInfoDtos 任务id的集合
     * @param token
     * @return
     */
    @PostMapping("/receive")
    public MetaRestResponse receiveTask(@RequestBody List<TaskInfoDto> taskInfoDtos, @RequestHeader("token") String token){

        LOGGER.info("进入【receiveTask】方法中了，请求参数为：{}",JSONObject.toJSONString(taskInfoDtos,true));
        taskService.receiveTask(taskInfoDtos,token);
        LOGGER.info("退出【receiveTask】方法.......");
        return MetaRestResponse.success(ResponseCode.SUCCESS,"接收任务成功！");
    }

    /**
     * 送货员完成任务
     * 批量处理
     * @param taskInfoDtos 任务id的集合
     * @param token
     * @return
     */
    @PostMapping("/receive")
    public MetaRestResponse compeleteTask(@RequestBody List<TaskInfoDto> taskInfoDtos, @RequestHeader("token") String token){

        LOGGER.info("进入【receiveTask】方法中了，请求参数为：{}",JSONObject.toJSONString(taskInfoDtos,true));
        taskService.compeleteTask(taskInfoDtos,token);
        LOGGER.info("退出【receiveTask】方法.......");
        return MetaRestResponse.success(ResponseCode.SUCCESS,"接收任务成功！");
    }
}
