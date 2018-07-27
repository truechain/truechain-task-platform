package com.truechain.task.admin.controller;

import com.truechain.task.admin.service.AuthUserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthRole;
import com.truechain.task.model.entity.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * 添加用户
     */
    @PostMapping("addUser")
    public Wrapper addUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuthUser user) {
        authUserService.addUser(user);
        return WrapMapper.ok();
    }

    /**
     * 获取用户列表
     */
    @PostMapping("/getUserPage")
    public Wrapper getUserPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<AuthUser> userPage = authUserService.getUserPageByCriteria(null, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取对应用户角色
     */
    @GetMapping("/getUserRoleList")
    public Wrapper getUserRoleList(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        AuthUser user = authUserService.getUserById(userId);
        if (null == user) {
            throw new BusinessException("用户不存在");
        }
        List<AuthRole> roleList = user.getRoles();
        return WrapMapper.ok(roleList);
    }

    /**
     * 获取角色关联的(roleId)对应用户列表
     */
    @GetMapping("/getUserListByRoleId")
    public Wrapper getUserListByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam List<Integer> roleIdList, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthUser authUser = new AuthUser();
        authUser.setRoleIdList(roleIdList);
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


    /**
     * 用户登出
     */
    @GetMapping("/exit")
    public Wrapper accountExit(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {

        return WrapMapper.ok();
    }
}
