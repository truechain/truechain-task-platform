package com.truechain.task.admin.controller;

import com.truechain.task.admin.core.WrapMapper;
import com.truechain.task.admin.core.Wrapper;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.admin.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户记录
     */
    @RequestMapping("/getUserPage")
    public Wrapper getUserPage(@RequestParam int pageIndex, @RequestParam int pageSize) {

        return WrapMapper.ok();
    }

    /**
     * 获取用户详情
     */
    @RequestMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestParam Long userId) {

        return WrapMapper.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/updateUser")
    public Wrapper updateUser(@RequestBody SysUser user) {

        return WrapMapper.ok();
    }

    /**
     * 审核用户
     */
    @RequestMapping("/auditUser")
    public Wrapper auditUser(Long userId) {

        return WrapMapper.ok();
    }
}