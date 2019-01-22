package com.inventory.inventory.task.controller;

import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.task.dto.TaskInfoDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:shixianqing
 * @Date:2019/1/2117:45
 * @Description:采购任务控制器
 **/
@RestController
@RequestMapping(value = "/task",produces = {"application/json;charset=utf-8"})
public class TaskController {

    @PostMapping("/add")
    public MetaRestResponse addTask(@RequestBody TaskInfoDto taskInfoDto) {


        return null;
    }
}
