package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户Controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 个人详细
     */
    @GetMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestParam(required = true) Integer rewardType) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId, rewardType);
        return WrapMapper.ok(userInfoDTO);
    }

    /**
     * 获取推荐记录列表
     */
    @GetMapping("/getRecommendUserList")
    public Wrapper getRecommendUserList() {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        List<RecommendTaskDTO> recommendTaskDTOList = userService.getRecommendUserList(userId);
        return WrapMapper.ok(recommendTaskDTOList);
    }
}
