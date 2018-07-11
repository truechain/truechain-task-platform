package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 任务Controller
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController extends BasicController {

    @Autowired
    private TaskService taskService;


    /**
     * 获取任务详情
     */
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo(@RequestParam Long taskId) {
        TaskDTO taskDTO = taskService.getTaskInfo(taskId);
        return WrapMapper.ok(taskDTO);
    }

    /**
     * 抢任务
     */
    @PostMapping("/holdTask")
    public Wrapper holdTask(@RequestParam Long taskId) {
        return WrapMapper.ok();
    }

    /**
     * 获取我的任务列表
     */
    @PostMapping("/getUserTaskList")
    public Wrapper getUserTaskList() {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        TaskTotalDTO taskTotalDTO = taskService.getUserTaskList(userId);
        return WrapMapper.ok(taskTotalDTO);
    }

    /**
     * 获取我的任务详情
     */
    @PostMapping("/getUserTaskInfo")
    public Wrapper getUserTaskInfo(@RequestParam Long taskId) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        UserTaskInfoDTO userTaskInfoDTO = taskService.getUserTaskInfo(userId, taskId);
        return WrapMapper.ok(userTaskInfoDTO);
    }

    /**
     * 提交任务
     */
    @PostMapping("/commitUserTask")
    public Wrapper commitUserTask(@RequestParam Long taskId, @RequestParam(required = false) String commitAddress, @RequestParam(required = false) String remark) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        taskService.commitUserTask(userId, taskId, commitAddress, remark);
        return WrapMapper.ok();
    }

}
