package com.truechain.task.admin.service;

import com.truechain.task.admin.model.viewPojo.RoleInfo;
import com.truechain.task.model.entity.AuthRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    Page<AuthRole> getRolePageByCriteria(AuthRole role, Pageable pageable);

    List<AuthRole> getListRoleByCriteria(AuthRole role);

    RoleInfo getRoleInfo(Long roleId);

    void addRole(AuthRole role);

    void updateRole(AuthRole role);

    void deleteRoleByRoleId(Long roleId);
}
