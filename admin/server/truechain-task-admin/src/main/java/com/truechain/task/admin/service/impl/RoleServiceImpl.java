package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.viewPojo.RoleInfo;
import com.truechain.task.admin.repository.AuthResourceRepository;
import com.truechain.task.admin.repository.AuthRoleRepository;
import com.truechain.task.admin.service.RoleService;
import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.model.entity.AuthRole;
import com.truechain.task.model.entity.QAuthRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色service
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AuthRoleRepository roleRepository;

    @Autowired
    private AuthResourceRepository resourceRepository;

    @Override
    public Page<AuthRole> getRolePageByCriteria(AuthRole role, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QAuthRole qAuthRole = QAuthRole.authRole;
        if (role.getStatus() != null) {
            builder.and(qAuthRole.status.eq(role.getStatus()));
        }
        if (StringUtils.isNotBlank(role.getName())) {
            builder.and(qAuthRole.name.like(role.getName() + "%"));
        }
        Page<AuthRole> rolePage = roleRepository.findAll(builder, pageable);
        return rolePage;
    }

    @Override
    public List<AuthRole> getListRoleByCriteria(AuthRole role) {
        QAuthRole qAuthRole = QAuthRole.authRole;
        BooleanBuilder builder = new BooleanBuilder();
        if (role.getStatus() != null) {
            builder.and(qAuthRole.status.eq(role.getStatus()));
        }
        Iterable<AuthRole> roleIterable = roleRepository.findAll(builder);
        List<AuthRole> roleList = new ArrayList<>();
        if (roleIterable != null) {
            roleIterable.forEach(x -> roleList.add(x));
        }
        return roleList;
    }

    @Override
    public RoleInfo getRoleInfo(Long roleId) {
        RoleInfo roleInfo = new RoleInfo();
        AuthRole role = roleRepository.findOne(roleId);
        Preconditions.checkArgument(role != null, "角色不存在");
        roleInfo.setRole(role);
        roleInfo.setResources(role.getResources());
        return roleInfo;
    }

    @Override
    @Transactional
    public void addRole(AuthRole role) {
        List<Long> resourceIdList = role.getResourceIdList();
        role.setResources(new ArrayList<>());
        for (Long resourceId : resourceIdList) {
            AuthResource resource = resourceRepository.findOne(resourceId);
            Preconditions.checkArgument(resource != null, "权限不存在");
            role.getResources().add(resource);
        }
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void updateRole(AuthRole role) {
        AuthRole authRole = roleRepository.findOne(role.getId());
        Preconditions.checkArgument(authRole != null, "角色不存在");
        authRole.setName(role.getName());
        authRole.setCode(role.getCode());
        authRole.setStatus(role.getStatus());
        authRole.setResources(new ArrayList<>());
        List<Long> resourceIdList = role.getResourceIdList();
        if (resourceIdList != null) {
            for (Long resourceId : resourceIdList) {
                AuthResource resource = resourceRepository.findOne(resourceId);
                Preconditions.checkArgument(resource != null, "权限不存在");
                authRole.getResources().add(resource);
            }
        }
        roleRepository.save(authRole);
    }

    @Override
    public void deleteRoleByRoleId(Long roleId) {
        AuthRole role = roleRepository.findOne(roleId);
        Preconditions.checkArgument(role != null, "角色不存在");
        Preconditions.checkArgument(CollectionUtils.isEmpty(role.getUsers()), "已有用户绑定此角色");
        roleRepository.delete(role);
    }
}
