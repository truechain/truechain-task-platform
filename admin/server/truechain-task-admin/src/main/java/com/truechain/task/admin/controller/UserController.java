package com.truechain.task.admin.controller;

import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<SysUser> userPage = userService.getUserPage(null, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取用户详情
     */
    @RequestMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestParam Long userId) {
        SysUser user = userService.getUserInfo(userId);
        return WrapMapper.ok(user);
    }

    /**
     * 修改用户
     */
    @RequestMapping("/updateUser")
    public Wrapper updateUser(@RequestBody SysUser user) {
        userService.updateUser(user);
        return WrapMapper.ok();
    }

    /**
     * 审核用户
     */
    @RequestMapping("/auditUser")
    public Wrapper auditUser(Long userId) {
        userService.auditUser(userId);
        return WrapMapper.ok();
    }
}
