package com.truechain.task.admin.controller;

import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.admin.service.TaskService;
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
    public Wrapper getTaskPage(@RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<BsTask> taskPage = taskService.getTaskPage(null, pageable);
        return WrapMapper.ok(taskPage);
    }

    /**
     * 获取任务详情
     */
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo(@RequestParam Long taskId) {
        BsTask task = taskService.getTaskInfo(taskId);
        return WrapMapper.ok(task);
    }

    /**
     * 获取报名表信息
     */
    public Wrapper getEntryFormInfo() {
        return WrapMapper.ok();
    }

    /**
     * 新增任务
     */
    @PostMapping("/addTask")
    public Wrapper addTask(@RequestBody BsTask task) {
        taskService.addTask(task);
        return WrapMapper.ok();
    }

    /**
     * 更新任务
     */
    @PostMapping("/updateTask")
    public Wrapper updateTask(@RequestBody BsTask task) {
        taskService.updateTask(task);
        return WrapMapper.ok();
    }

    /**
     * 删除任务
     */
    @PostMapping("/deleteTask")
    public Wrapper deleteTask(@RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 启用任务
     */
    @PostMapping("/enableTask")
    public Wrapper enableTask(@RequestParam Long taskId) {
        taskService.enableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 禁用任务
     */
    @PostMapping("/disableTask")
    public Wrapper disableTask(@RequestParam Long taskId) {
        taskService.disableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 审核报名表人员
     */
    public Wrapper auditEntryFormUser() {
        return WrapMapper.ok();
    }
}
