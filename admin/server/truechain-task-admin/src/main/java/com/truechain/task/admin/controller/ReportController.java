package com.truechain.task.admin.controller;

import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表Controller
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController extends BasicController {

    /**
     * 获取用户概况数据
     */
    @PostMapping("/getUserProfilePage")
    public Wrapper getUserProfilePage() {

        return WrapMapper.ok();
    }

    /**
     * 获取奖励统计数据
     */
    @PostMapping("/getRewardStats")
    public Wrapper getRewardStats() {

        return WrapMapper.ok();
    }

    /**
     * 获取推荐统计数据
     */
    @PostMapping("/getRecommendStats")
    public Wrapper getRecommendStats() {
        return WrapMapper.ok();
    }

    /**
     * 获取任务统计数据
     */
    @PostMapping("/getTaskStats")
    public Wrapper getTaskStats() {
        return WrapMapper.ok();
    }
}
