package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public Wrapper getUserInfo() {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        return WrapMapper.ok(userInfoDTO);
    }
}
