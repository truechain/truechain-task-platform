package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.config.AppProperties;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.dto.UserDetailDTO;
import com.truechain.task.admin.repository.BsRecommendTaskRepository;
import com.truechain.task.admin.repository.BsUserAccountDetailRepository;
import com.truechain.task.admin.repository.BsUserAccountRepository;
import com.truechain.task.admin.repository.SysUserRepository;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.AuditStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private BsUserAccountRepository userAccountRepository;

    @Autowired
    private BsUserAccountDetailRepository userAccountDetailRepository;

    @Autowired
    private BsRecommendTaskRepository recommendTaskRepository;

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
                builder.and(qSysUser.auditStatus.eq(AuditStatusEnum.UNCOMPLATE.getCode()).or(qSysUser.auditStatus.eq(AuditStatusEnum.UNAUDITED.getCode())));
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

        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "updateTime");
        Page<SysUser> userPage = userRepository.findAll(builder, pageable);

        return userPage;
    }

    @Override
    public Page<SysUser> getAuditedUserPage(UserDTO user, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QSysUser qSysUser = QSysUser.sysUser;
        if (StringUtils.isNotBlank(user.getLevel())) {
            builder.and(qSysUser.level.eq(user.getLevel()));
        }
//        if (null != user.getAuditStatus()) {
//            if (user.getAuditStatus() == 1) {
        builder.and(qSysUser.auditStatus.eq(1));
//            } else if (user.getAuditStatus() == 0) {
////                builder.and(qSysUser.auditStatus.eq(AuditStatusEnum.UNCOMPLATE.getCode()).or(qSysUser.auditStatus.eq(AuditStatusEnum.UNAUDITED.getCode())));
//            }
//        }
        if (StringUtils.isNotBlank(user.getStartDate())) {
            builder.and(qSysUser.auditPassTime.gt(user.getStartDate()));
        }
        if (StringUtils.isNotBlank(user.getEndDate())) {
            builder.and(qSysUser.auditPassTime.lt(user.getEndDate()));
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
    public UserDetailDTO getUserInfo(Long userId) {
        SysUser user = userRepository.findOne(userId);
        String resumeFilePath = user.getResumeFilePath();
        if (StringUtils.isNotBlank(resumeFilePath)) {
            resumeFilePath = resumeFilePath.substring(resumeFilePath.lastIndexOf("/"));
            user.setResumeFilePath(AppProperties.RESUME_URL + resumeFilePath);
        }
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setSysUser(user);

        if (user.getRecommendUserId() != 0) {
            SysUser recommendUser = userRepository.findOne(user.getRecommendUserId());
            userDetailDTO.setRefererPhone(recommendUser.getMobile());
            userDetailDTO.setRefererWXName(recommendUser.getWxNickName());
            userDetailDTO.setRefererAddress(recommendUser.getTrueChainAddress());
        }
        return userDetailDTO;
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
        sysUser.setUpdatetime(user.getUpdateTime());
        userRepository.save(sysUser);
        return sysUser;
    }

    @Override
    @Transactional
    public void auditUser(Long userId, String level, String rewardNum) {
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        Preconditions.checkArgument(AuditStatusEnum.UNAUDITED.getCode() == sysUser.getAuditStatus(), "用户已通过审核");
        sysUser.setAuditStatus(AuditStatusEnum.AUDITED.getCode());
        sysUser.setAuditPassTime(new Date().toString());
        sysUser.setLevel(level);
        userRepository.save(sysUser);
//        BsRecommendTask recommendTask = recommendTaskRepository.getByUser(sysUser);
//        Preconditions.checkArgument(null != recommendTask, "用户推荐信息未完善");
        //发放奖励
        BsUserAccount userAccount = userAccountRepository.getByUser(sysUser);
        Preconditions.checkArgument(null != userAccount, "用户账户不存在");
        userAccount.setGitReward(userAccount.getGitReward().add(new BigDecimal(rewardNum)));
        userAccountRepository.save(userAccount);
        BsUserAccountDetail userAccountDetail = new BsUserAccountDetail();
        userAccountDetail.setUserAccount(userAccount);
        userAccountDetail.setRewardType(3);
//        userAccountDetail.setRecommendTask(recommendTask);
        userAccountDetail.setRewardNum(new BigDecimal(rewardNum));
        userAccountDetailRepository.save(userAccountDetail);
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
