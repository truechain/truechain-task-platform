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

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

    @Override
    public Page<SysUser> getUserPage(UserDTO user, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QSysUser qSysUser = QSysUser.sysUser;
        if (StringUtils.isNotBlank(user.getLevel())) {
            builder.and(qSysUser.level.eq(user.getLevel()));
        }
        if (null != user.getAuditStatus()) {
            if (user.getAuditStatus() == 1) {
                builder.and(qSysUser.auditStatus.eq(user.getAuditStatus()));
            } else if (user.getAuditStatus() == 0) {
//                builder.and(qSysUser.auditStatus.eq(AuditStatusEnum.UNCOMPLATE.getCode()).or(qSysUser.auditStatus.eq(AuditStatusEnum.UNAUDITED.getCode())));
            }
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
    public SysUser getUser(Long userId) {
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(user != null, "用户不存在");
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        SysUser sysUser = userRepository.findOne(user.getId());
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        sysUser.setLevel(user.getLevel());
        userRepository.save(sysUser);
        return sysUser;
    }

    @Override
    public void auditUser(Long userId, String level, String rewardNum) {
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        Preconditions.checkArgument(AuditStatusEnum.UNAUDITED.getCode() == sysUser.getAuditStatus(), "用户已通过审核");
        sysUser.setAuditStatus(AuditStatusEnum.AUDITED.getCode());
        sysUser.setAuditPassTime(new Date().toString());
        sysUser.setLevel(level);
        userRepository.save(sysUser);
    }

    @Override
    public long countPartTimeTotalPeople() {
        return userRepository.count();
    }

    public long countAuditPass(String beginDate, String endDate) {
        BooleanBuilder builder = new BooleanBuilder();
        QSysUser qSysUser = QSysUser.sysUser;

        builder.and(qSysUser.auditStatus.eq(1));            //审核状态(-1-未审核,0-未完善个人信息,1-已审核)
        if (StringUtils.isNotBlank(beginDate)) {
            builder.and(qSysUser.updateTime.gt(beginDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            builder.and(qSysUser.updateTime.lt(endDate));
        }

        long count = userRepository.count(builder);
        return count;
    }
}
