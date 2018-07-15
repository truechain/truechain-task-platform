package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task/unauth")
public class UnAuthController extends BasicController {

    @Autowired
    private TaskService taskService;

    /**
     * 获取任务分页数据
     */
    @PostMapping("/getTaskPage")
    public Wrapper getTaskPage(@RequestParam(required = false) String taskName, @RequestParam(required = false) Integer category, @RequestParam(required = false) String level,
                               @RequestParam(required = false) Integer reward, @RequestParam int pageIndex, @RequestParam int pageSize) {
        BsTask task = new BsTask();
        task.setName(taskName);
        task.setCategory(category);
        task.setLevel(level);
        task.setRewardType(reward);
        Page<BsTask> taskPage = taskService.getTaskPage(task, pageIndex, pageSize);
        return WrapMapper.ok(taskPage);
    }


    /**
     * 获取任务详情
     */
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo(@RequestParam Long taskId) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = null;
        if (null != sessionPOJO) {
            userId = sessionPOJO.getUserId();
        }
        TaskDTO taskDTO = taskService.getTaskInfo(taskId, userId);
        return WrapMapper.ok(taskDTO);
    }
}
