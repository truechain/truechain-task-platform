package com.truechain.task.admin.service;

import com.truechain.task.model.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /**
     * 获取用户记录
     *
     * @param user
     * @param pageable
     */
    Page<SysUser> getUserPage(SysUser user, Pageable pageable);

    /**
     * 获取用户详情
     *
     * @param userId
     */
    SysUser getUserInfo(Long userId);

    /**
     * 修改用户
     *
     * @param user
     */
    SysUser updateUser(SysUser user);

    /**
     * 审核用户
     *
     * @param userId
     */
    void auditUser(Long userId);

}
