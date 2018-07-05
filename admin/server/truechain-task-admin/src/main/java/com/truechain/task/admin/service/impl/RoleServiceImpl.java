package com.truechain.task.admin.service.impl;

import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.admin.repository.AuthRoleRepository;
import com.truechain.task.admin.service.RoleService;
import com.truechain.task.model.entity.AuthRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public void addRoleResource(Integer roleId, Long resourceId) {
        AuthRole role = roleRepository.findOne(roleId);
        AuthResource authResource = new AuthResource();
        authResource.setId(resourceId);
        role.getResources().add(authResource);
        roleRepository.save(role);
    }

    @Override
    public void deleteRoleResource(Integer roleId, Long resourceId) {
        AuthRole role = roleRepository.findOne(roleId);
        AuthResource authResource = new AuthResource();
        authResource.setId(resourceId);
        role.getResources().remove(authResource);
        roleRepository.save(role);
    }

    @Override
    public Page<AuthRole> getRolePageByCriteria(AuthRole role, Pageable pageable) {
        Page<AuthRole> rolePage = roleRepository.findAll(pageable);
        return rolePage;
    }

    @Override
    public List<AuthRole> getListRoleByCriteria(AuthRole role) {
        return null;
    }

    @Override
    public void addRole(AuthRole role) {
        roleRepository.save(role);
    }

    @Override
    public void updateRole(AuthRole role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRoleByRoleId(Integer roleId) {
        roleRepository.delete(roleId);
    }
}
