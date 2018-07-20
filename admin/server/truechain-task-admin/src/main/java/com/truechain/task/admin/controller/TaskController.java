package com.truechain.task.admin.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TaskInfoDTO;
import com.truechain.task.admin.service.TaskService;
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
@RequestMapping("/tast")
public class TaskController extends BasicController {

    @Autowired
    private TaskService taskService;

    /**
     * 获取任务数据
     */
    @PostMapping("/getTaskPage")
    public Wrapper getTaskPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskDTO task) {
        Preconditions.checkArgument(task.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(task.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(task.getPageIndex() - 1, task.getPageSize());
        Page<BsTask> taskPage = taskService.getTaskPage(task, pageable);
        return WrapMapper.ok(taskPage);
    }

    /**
     * 获取任务详情
     */
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        TaskInfoDTO task = taskService.getTaskInfo(taskId);
        return WrapMapper.ok(task);
    }

    /**
     * 获取报名表信息
     */
    public Wrapper getEntryFormInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {
        return WrapMapper.ok();
    }

    /**
     * 新增任务
     */
    @PostMapping("/addTask")
    public Wrapper addTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody BsTask task) {
        taskService.addTask(task);
        return WrapMapper.ok();
    }

    /**
     * 更新任务
     */
    @PostMapping("/updateTask")
    public Wrapper updateTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody BsTask task) {
        taskService.updateTask(task);
        return WrapMapper.ok();
    }

    /**
     * 删除任务
     */
    @PostMapping("/deleteTask")
    public Wrapper deleteTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 启用任务
     */
    @PostMapping("/enableTask")
    public Wrapper enableTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.enableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 禁用任务
     */
    @PostMapping("/disableTask")
    public Wrapper disableTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.disableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 审核报名表人员
     */
    public Wrapper auditEntryFormUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {
        return WrapMapper.ok();
    }
}
