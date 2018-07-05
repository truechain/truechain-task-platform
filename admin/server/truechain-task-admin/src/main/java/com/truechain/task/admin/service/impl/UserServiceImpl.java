package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.model.enums.AuditStatusEnum;
import com.truechain.task.admin.repository.SysUserRepository;
import com.truechain.task.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public Page<SysUser> getUserPage(SysUser user, Pageable pageable) {
        Page<SysUser> userPage = userRepository.findAll(pageable);
        return userPage;
    }

    @Override
    public SysUser getUserInfo(Long userId) {
        SysUser user = userRepository.findOne(userId);
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        SysUser sysUser = userRepository.findOne(user.getId());
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        sysUser.setPersonName(user.getPersonName());
        userRepository.save(sysUser);
        return sysUser;
    }

    @Override
    public void auditUser(Long userId) {
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        sysUser.setAuditStatus(AuditStatusEnum.AUDITED.getCode());
        userRepository.save(sysUser);
    }
}
