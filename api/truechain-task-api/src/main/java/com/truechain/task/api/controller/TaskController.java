package com.truechain.task.api.controller;

import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务Controller
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController extends BasicController {

    @Autowired
    private TaskService taskService;

    /**
     * 获取任务分页数据
     */
    @PostMapping("/getTaskPage")
    public Wrapper getTaskPage(@RequestParam(required = false) String taskName, @RequestParam(required = false) int category, @RequestParam(required = false) int level,
                               @RequestParam(required = false) String reward, @RequestParam int pageIndex, @RequestParam int pageSize) {
        BsTask task = new BsTask();
        task.setName(taskName);
        task.setCategory(category);
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<BsTask> taskPage = taskService.getTaskPage(task, pageable);
        return WrapMapper.ok(taskPage);
    }

    /**
     * 获取任务详情
     */
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo() {
        return WrapMapper.ok();
    }

    /**
     * 抢任务
     */
    @PostMapping("/holdTask")
    public Wrapper holdTask() {
        return WrapMapper.ok();
    }

}
