package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.dto.UserDetailDTO;
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
    Page<SysUser> getUserPage(UserDTO user, Pageable pageable);

    /**
     * 获取已经审核用户记录
     * @param user
     * @param pageable
     * @return
     */
    Page<SysUser> getAuditedUserPage(UserDTO user, Pageable pageable);

    /**
     * 获取用户详情
     *
     * @param userId
     */
    UserDetailDTO getUserInfo(Long userId);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    SysUser getUser(Long userId);
    
    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    SysUser getUserByMobile(String mobile);
    
    /**
     * 创建用户
     *
     * @param user
     */
    SysUser addUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user
     */
    SysUser updateUser(SysUser user);
    
    /**
     * 修改用户的等级
     *
     * @param user
     */
    SysUser updateUserLevel(SysUser user);

    /**
     * 修改用户的黑名状态
     *
     * @param user
     */
    SysUser updateUserBlank(SysUser user);
    
    /**
     * 审核用户
     *
     * @param userId
     * @param level
     * @param rewardNum
     */
    void auditUser(Long userId, String level, String rewardNum);

    /**
     * 获取兼职总人数
     *
     * @return
     */
    long countPartTimeTotalPeople();

    long countAuditPass(String beginDate, String endDate);
}
