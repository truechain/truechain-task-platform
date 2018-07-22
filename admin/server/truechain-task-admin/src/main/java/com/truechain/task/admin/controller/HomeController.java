package com.truechain.task.admin.controller;

import com.truechain.task.admin.service.TaskService;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页Controller
 */
@RestController
@RequestMapping("/home")
public class HomeController extends BasicController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    /**
     * 获取兼职总人数
     */
    @GetMapping("/countPartTimeTotalPeople")
    public Wrapper countPartTimeTotalPeople(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {
        long count = userService.countPartTimeTotalPeople();
        return WrapMapper.ok(count);
    }

    /**
     * 获取总任务数
     */
    @GetMapping("/countTotalTask")
    public Wrapper countTotalTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {
        long count = taskService.countTotalTask();
        return WrapMapper.ok(count);
    }

    /**
     * 获取完成任务数
     */
    @GetMapping("/countComplateTask")
    public Wrapper countComplateTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {
        return WrapMapper.ok(243);
    }
}
