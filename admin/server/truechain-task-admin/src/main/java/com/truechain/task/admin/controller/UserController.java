package com.truechain.task.admin.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/getUserPage")
    public Wrapper getUserPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {
        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getUserPage(user, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取用户详情
     */
    @PostMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        SysUser user = userService.getUserInfo(userId);
        return WrapMapper.ok(user);
    }

    /**
     * 修改用户
     */
    @PostMapping("/updateUser")
    public Wrapper updateUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam long userId, @RequestParam String level) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setLevel(level);
        userService.updateUser(user);
        return WrapMapper.ok();
    }

    /**
     * 审核用户
     */
    @PostMapping("/auditUser")
    public Wrapper auditUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId, @RequestParam String rewardNum) {
        userService.auditUser(userId, rewardNum);
        return WrapMapper.ok();
    }
}
