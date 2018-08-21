package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.repository.AuthRoleRepository;
import com.truechain.task.admin.repository.AuthUserRepository;
import com.truechain.task.admin.service.AuthUserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.AuthRole;
import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.model.entity.QAuthUser;
import com.truechain.task.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    @Override
    public void addAuthUser(AuthUser user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            String password = user.getPassword();
            user.setPassword(MD5Util.generate(password));
        }
        long count = authUserRepository.countByUsername(user.getUsername());
        if (count > 0) {
            throw new BusinessException("用户名已被占用");
        }
        if (!StringUtils.isEmpty(user.getRoleId())) {
            user.setRoles(new ArrayList<>());
            AuthRole role = authRoleRepository.findOne(user.getRoleId());
            Preconditions.checkArgument(role != null, "角色不存在");
            user.getRoles().add(role);
        }
        authUserRepository.save(user);
    }

    @Override
    public void updateAuthUser(AuthUser user) {
        AuthUser authUser = authUserRepository.findOne(user.getId());
        Preconditions.checkArgument(authUser != null, "用户不存在");
        if (!StringUtils.isEmpty(user.getPassword())) {
            String password = user.getPassword();
            authUser.setPassword(MD5Util.generate(password));
        }
        authUser.setRealName(user.getRealName());
        authUser.setPhone(user.getPhone());
        authUser.setRemark(user.getRemark());
        long count = authUserRepository.countByUsernameAndIdIsNot(user.getUsername(), user.getId());
        if (count > 0) {
            throw new BusinessException("用户名已被占用");
        }
        if (!StringUtils.isEmpty(user.getRoleId())) {
            user.setRoles(new ArrayList<>());
            AuthRole role = authRoleRepository.findOne(user.getRoleId());
            Preconditions.checkArgument(role != null, "角色不存在");
            user.getRoles().add(role);
        }
        authUserRepository.save(authUser);
    }

    @Override
    public void deleteAuthUser(Long userId) {
        AuthUser authUser = authUserRepository.findOne(userId);
        Preconditions.checkArgument(authUser != null, "用户不存在");
        authUserRepository.delete(authUser);
    }

    @Override
    public AuthUser getUserById(Long userId) {
        AuthUser authUser = authUserRepository.findOne(userId);
        Preconditions.checkArgument(authUser != null, "用户不存在");
        if (!CollectionUtils.isEmpty(authUser.getRoles())) {
            authUser.setRoleName(authUser.getRoles().get(0).getName());
        }
        return authUser;
    }

    @Override
    public Page<AuthUser> getUserPageByCriteria(AuthUser user, Pageable pageable) {
        QAuthUser qAuthUser = QAuthUser.authUser;
        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(user.getUsername())) {
            builder.and(qAuthUser.username.like(user.getUsername() + "%"));
        }
        if (!StringUtils.isEmpty(user.getRealName())) {
            builder.and(qAuthUser.realName.like(user.getRealName() + "%"));
        }
        if (!StringUtils.isEmpty(user.getPhone())) {
            builder.and(qAuthUser.phone.like(user.getPhone() + "%"));
        }
        if (!StringUtils.isEmpty(user.getRoleName())) {
            builder.and(qAuthUser.roles.any().name.like(user.getRoleName() + "%"));
        }
        Page<AuthUser> authUserPage = authUserRepository.findAll(builder, pageable);
        for (AuthUser authUser : authUserPage.getContent()) {
            if (!CollectionUtils.isEmpty(authUser.getRoles())) {
                authUser.setRoleName(authUser.getRoles().get(0).getName());
            }
        }
        return authUserPage;
    }


    @Override
    public void addUserRole(Long userId, Long roleId) {
        AuthUser user = getUserById(userId);
        if (null == user) {
            throw new BusinessException("用户不存在");
        }
        AuthRole role = new AuthRole();
        role.setId(roleId);
        if (CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(Collections.singletonList(role));
        } else {
            user.getRoles().add(role);
        }
        authUserRepository.save(user);
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        AuthUser user = getUserById(userId);
        if (null == user) {
            throw new BusinessException("用户不存在");
        }
        if (user.getUsername().equals("admin")) {
            throw new BusinessException("管理员不允许更改");
        }
        if (CollectionUtils.isEmpty(user.getRoles())) {
            AuthRole role = new AuthRole();
            role.setId(roleId);
            user.getRoles().remove(role);
        }
        authUserRepository.save(user);
    }

}
