package com.truechain.task.admin.controller;

import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.admin.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 资源Controller
 */
@RestController
@RequestMapping("/admin/resource")
public class AdminResourceController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(AdminResourceController.class);

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取角色(roleId)所被授权的资源
     */
    @PostMapping("/getResourcesByRoleId")
    public Wrapper getResourcesByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestParam Integer roleId, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthResource authResource = new AuthResource();
        authResource.setRoleId(roleId);
        Page<AuthResource> resourcePage = resourceService.getResourcePageByCriteria(authResource, pageable);
        return WrapMapper.ok(resourcePage);
    }

    /**
     * 获取角色(roleId)未被授权的资源
     */
    @PostMapping("/getResourcesExtendByRoleId")
    public Wrapper getResourcesExtendByRoleId(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestParam Integer roleId, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        AuthResource authResource = new AuthResource();
        authResource.setRoleId(roleId);
        Page<AuthResource> resourcePage = resourceService.getResourcePageByCriteria(authResource, pageable);
        return WrapMapper.ok(resourcePage);
    }

    /**
     * 获取用户授权的菜单
     */
    @PostMapping("/")
    public Wrapper getAuthorityMenu() {

        return WrapMapper.ok();
    }

    /**
     * 获取全部菜单
     */
    @PostMapping("/getResourcePage")
    public Wrapper getResourcePage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<AuthResource> resourcePage = resourceService.getResourcePageByCriteria(null, pageable);
        return WrapMapper.ok(resourcePage);
    }

    /**
     * 添加菜单
     */
    @PostMapping("/addResource")
    public Wrapper addResource(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody AuthResource resource) {
        resourceService.addResource(resource);
        return WrapMapper.ok();
    }

    /**
     * 修改菜单
     */
    @PostMapping("/updateResource")
    public Wrapper updateResource(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody AuthResource resource) {
        resourceService.updateResource(resource);
        return WrapMapper.ok();
    }

    /**
     * 删除菜单
     */
    @PostMapping("/deleteResource")
    public Wrapper deleteResource(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestParam Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return WrapMapper.ok();
    }
}
