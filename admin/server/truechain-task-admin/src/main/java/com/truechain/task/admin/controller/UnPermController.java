package com.truechain.task.admin.controller;

import com.truechain.task.admin.model.viewPojo.ResourceTreeVO;
import com.truechain.task.admin.service.ResourceService;
import com.truechain.task.admin.service.RoleService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.model.entity.AuthRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unperm")
public class UnPermController extends BasicController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 角色下拉列表
     *
     * @return
     */
    @PostMapping("/admin/role/getRoleList")
    public Wrapper getRoleList() {
        AuthRole role = new AuthRole();
        role.setStatus((short) 1);
        List<AuthRole> roleList = roleService.getListRoleByCriteria(role);
        return WrapMapper.ok(roleList);
    }

    /**
     * 资源下拉列表
     *
     * @return
     */
    @PostMapping("/admin/resource/getResourceTreeList")
    public Wrapper getResourceTreeList(@RequestParam(required = false) Long resourceId) {
        AuthResource authResource = new AuthResource();
        authResource.setId(resourceId);
        authResource.setStatus((short) 1);
        List<ResourceTreeVO> resourceList = resourceService.getResourceTreeList(authResource);
        return WrapMapper.ok(resourceList);
    }
}
