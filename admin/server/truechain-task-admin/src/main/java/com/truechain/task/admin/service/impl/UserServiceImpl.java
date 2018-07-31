package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.repository.SysUserRepository;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.model.entity.QSysUser;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.model.enums.AuditStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public Page<SysUser> getUserPage(UserDTO user, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QSysUser qSysUser = QSysUser.sysUser;
        if (null != user.getAuditStatus()) {
            builder.and(qSysUser.auditStatus.eq(user.getAuditStatus()));
        }
        if (StringUtils.isNotBlank(user.getLevel())) {
            builder.and(qSysUser.level.eq(user.getLevel()));
        }
        if (StringUtils.isNotBlank(user.getStartDate())) {
            builder.and(qSysUser.createTime.gt(user.getStartDate()));
        }
        if (StringUtils.isNotBlank(user.getEndDate())) {
            builder.and(qSysUser.createTime.lt(user.getEndDate()));
        }
        if (StringUtils.isNotBlank(user.getName())) {
            builder.and(qSysUser.personName.like(user.getName() + "%"));
        }
        if (StringUtils.isNotBlank(user.getWxNickName())) {
            builder.and(qSysUser.wxNickName.like(user.getWxNickName() + "%"));
        }
        Page<SysUser> userPage = userRepository.findAll(builder, pageable);
        return userPage;
    }

    @Override
    public SysUser getUserInfo(Long userId) {
        SysUser user = userRepository.findOne(userId);
        String resumeFilePath = user.getResumeFilePath();
        if (StringUtils.isNotBlank(resumeFilePath)) {
            resumeFilePath = resumeFilePath.substring(resumeFilePath.lastIndexOf("/"));
            user.setResumeFilePath("http://phptrain.cn/resume" + resumeFilePath);
        }
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        SysUser sysUser = userRepository.findOne(user.getId());
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        sysUser.setPersonName(user.getLevel());
        userRepository.save(sysUser);
        return sysUser;
    }

    @Override
    public void auditUser(Long userId, String rewardNum) {
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        Preconditions.checkArgument(AuditStatusEnum.UNAUDITED.equals(sysUser.getAuditStatus()), "用户已通过审核");
        sysUser.setAuditStatus(AuditStatusEnum.AUDITED.getCode());
        userRepository.save(sysUser);
    }

    @Override
    public long countPartTimeTotalPeople() {
        return userRepository.count();
    }
}
