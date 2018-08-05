package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
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
    public AuthRole getRoleInfo(Long roleId) {
        AuthRole role = roleRepository.findOne(roleId);
        return role;
    }

    @Override
    @Transactional
    public void addRole(AuthRole role) {
        List<Long> resourceIdList = role.getResourceIdList();
        for (Long resourceId : resourceIdList) {
            AuthResource authResource = new AuthResource();
            authResource.setId(resourceId);
            role.getResources().add(authResource);
        }
        roleRepository.save(role);
    }

    @Override
    public void updateRole(AuthRole role) {
        AuthRole authRole = roleRepository.findOne(role.getId());
        Preconditions.checkArgument(authRole != null, "角色不存在");
        authRole.setName(role.getName());
        authRole.setCode(role.getCode());
        authRole.setStatus(role.getStatus());
        if (!CollectionUtils.isEmpty(authRole.getResourceIdList())) {
            authRole.getResourceIdList().clear();
        }
        authRole.setResourceIdList(new ArrayList<>());
        List<Long> resourceIdList = role.getResourceIdList();
        if (role.getResourceIdList() != null) {
            for (Long resourceId : resourceIdList) {
                AuthResource authResource = new AuthResource();
                authResource.setId(resourceId);
                authRole.getResources().add(authResource);
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
