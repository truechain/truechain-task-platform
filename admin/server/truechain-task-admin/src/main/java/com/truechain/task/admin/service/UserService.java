package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.UserDTO;
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
     * @param level
     * @param rewardNum
     */
    void auditUser(Long userId,String level, String rewardNum);

    /**
     * 获取兼职总人数
     *
     * @return
     */
    long countPartTimeTotalPeople();

    long countAuditPass(String beginDate,String endDate);
}
