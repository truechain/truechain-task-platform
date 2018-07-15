package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 抢任务
     */
    @PostMapping("/holdTask")
    public Wrapper holdTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskDetailId) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        taskService.holdTask(taskDetailId, userId);
        return WrapMapper.ok();
    }

    /**
     * 获取我的任务列表
     */
    @PostMapping("/getUserTaskList")
    public Wrapper getUserTaskList(@RequestParam(required = false) Integer taskStatus) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        TaskTotalDTO taskTotalDTO = taskService.getUserTaskList(userId, taskStatus);
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
