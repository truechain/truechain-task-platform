package com.truechain.task.admin.service;

import com.truechain.task.model.entity.AuthRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    void addRoleResource(Integer roleId, Long resourceId);

    void deleteRoleResource(Integer roleId, Long resourceId);

    Page<AuthRole> getRolePageByCriteria(AuthRole role, Pageable pageable);

    List<AuthRole> getListRoleByCriteria(AuthRole role);

    void addRole(AuthRole role);

    void updateRole(AuthRole role);

    void deleteRoleByRoleId(Integer roleId);
}
