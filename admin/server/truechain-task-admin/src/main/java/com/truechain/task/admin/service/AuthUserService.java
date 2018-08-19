package com.truechain.task.admin.service;

import com.truechain.task.model.entity.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthUserService {

    /**
     * 添加用户
     *
     * @param user
     */
    void addAuthUser(AuthUser user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateAuthUser(AuthUser user);

    /**
     * 删除用户
     *
     * @param userId
     */
    void deleteAuthUser(Long userId);

    AuthUser getUserById(Long userId);

    Page<AuthUser> getUserPageByCriteria(AuthUser user, Pageable pageable);

    void addUserRole(Long userId, Long roleId);

    void deleteUserRole(Long userId, Long roleId);
}
