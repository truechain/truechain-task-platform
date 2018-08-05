package com.truechain.task.admin.controller;

import com.truechain.task.admin.service.RoleService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 角色Controller
 */
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色
     */
    @PostMapping("/getRolePage")
    public Wrapper getRolePage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam(required = false) String name, @RequestParam(required = false) Short status,
                               @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthRole role = new AuthRole();
        role.setName(name);
        role.setStatus(status);
        Page<AuthRole> rolePage = roleService.getRolePageByCriteria(role, pageable);
        return WrapMapper.ok(rolePage);

    }

    /**
     * 获取角色详情
     */
    @PostMapping("/getRoleInfo")
    public Wrapper getRoleInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long roleId) {
        AuthRole role = roleService.getRoleInfo(roleId);
        return WrapMapper.ok(role);
    }

    /**
     * 添加角色
     */
    @PostMapping("/addRole")
    public Wrapper addRole(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuthRole role) {
        roleService.addRole(role);
        return WrapMapper.ok();
    }

    /**
     * 更新角色
     */
    @PostMapping("/updateRole")
    public Wrapper updateRole(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuthRole role) {
        roleService.updateRole(role);
        return WrapMapper.ok();
    }

    /**
     * 根据角色ID删除角色
     */
    @PostMapping("/deleteRole")
    public Wrapper deleteRoleByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long roleId) {
        roleService.deleteRoleByRoleId(roleId);
        return WrapMapper.ok();
    }


}
