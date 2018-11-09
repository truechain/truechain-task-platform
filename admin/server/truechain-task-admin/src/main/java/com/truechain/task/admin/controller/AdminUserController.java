package com.truechain.task.admin.controller;

import com.truechain.task.admin.service.AuthUserService;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.model.entity.SysUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AuthUserService authUserService;
    
    @Autowired
    private UserService userService;

    /**
     * 添加用户
     */
    @PostMapping("addUser")
    public Wrapper addUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuthUser user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new BusinessException("密码不能为空");
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new BusinessException("登录账号不能为空");
        }
        if (!user.getPassword().equals(user.getComfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }
        authUserService.addAuthUser(user);
        return WrapMapper.ok();
    }

    /**
     * 更新用户
     */
    @PostMapping("updateAuthUser")
    public Wrapper updateAuthUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuthUser user) {
        if (!StringUtils.isEmpty(user.getPassword()) && !user.getPassword().equals(user.getComfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }
        authUserService.updateAuthUser(user);
        return WrapMapper.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/deleteAuthUser")
    public Wrapper deleteAuthUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        authUserService.deleteAuthUser(userId);
        return WrapMapper.ok();
    }

    /**
     * 获取用户列表
     */
    @PostMapping("/getUserPage")
    public Wrapper getUserPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam(required = false) String userName, @RequestParam(required = false) String realName, @RequestParam(required = false) String phone, @RequestParam(required = false) String roleName,
                               @RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthUser authUser = new AuthUser();
        authUser.setUsername(userName);
        authUser.setRealName(realName);
        authUser.setPhone(phone);
        authUser.setRoleName(roleName);
        Page<AuthUser> userPage = authUserService.getUserPageByCriteria(authUser, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取用户详情
     */
    @PostMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        SysUser user = userService.getUser(userId);
        return WrapMapper.ok(user);
    }

    /**
     * 获取角色关联的(roleId)对应用户列表
     */
    @GetMapping("/getUserListByRoleId")
    public Wrapper getUserListByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long roleId, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthUser authUser = new AuthUser();
        authUser.setRoleId(roleId);
        Page<AuthUser> userPage = authUserService.getUserPageByCriteria(authUser, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取角色未关联的用户列
     */
    @GetMapping("/getUserListExtendByRoleId")
    public Wrapper getUserListExtendByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Integer roleId, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthUser authUser = new AuthUser();
        Page<AuthUser> userPage = authUserService.getUserPageByCriteria(authUser, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 给用户授权添加角色
     */
    @PostMapping("/authorityUserRole")
    public Wrapper authorityUserRole(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId, @RequestParam Long roleId) {
        authUserService.addUserRole(userId, roleId);
        return WrapMapper.ok();
    }

    /**
     * 删除已经授权的用户角色
     */
    @PostMapping("/deleteAuthorityUserRole")
    public Wrapper deleteAuthorityUserRole(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId, @RequestParam Long roleId) {
        authUserService.deleteUserRole(userId, roleId);
        return WrapMapper.ok();
    }


}
