package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.UserAccountDTO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.repository.BsRecommendTaskRepository;
import com.truechain.task.api.repository.BsUserAccountDetailRepository;
import com.truechain.task.api.repository.BsUserAccountRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.NullException;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.AuditStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    public SysUser getUserByOpenId(String openId) {
        SysUser sysUser = userRepository.findByOpenId(openId);
//        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        if (sysUser == null) {
            throw new NullException("该用户不存在");
        }
        return sysUser;
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        SysUser sysUser = userRepository.findByMobile(mobile);
        return sysUser;
    }

    @Override
    public SysUser addUser(SysUser user) {
        long count = userRepository.countByMobile(user.getMobile());
        if (count > 0) {
            throw new BusinessException("手机号已经注册");
        }
        user = userRepository.save(user);
        return user;
    }

    @Override
    public SysUser updateUser(SysUser user) {
        SysUser sysUser = userRepository.findOne(user.getId());
//        Preconditions.checkArgument(null != sysUser, "用户不存在");
        if (sysUser == null) {
            throw new NullException("用户不存在");
        }
        sysUser.setPersonName(user.getPersonName());
        sysUser.setWxNickName(user.getWxNickName());
        sysUser.setWxNum(user.getWxNum());
        sysUser.setOpenId(user.getOpenId());
        sysUser.setTrueChainAddress(user.getTrueChainAddress());
        sysUser.setResumeFilePath(user.getResumeFilePath());
        sysUser.setAuditStatus(AuditStatusEnum.UNAUDITED.getCode());
        sysUser.setRecommendUserId(user.getRecommendUserId());
        sysUser.setRecommendUserMobile(user.getRecommendUserMobile());
        sysUser = userRepository.save(sysUser);
        BsUserAccount userAccount = new BsUserAccount();
        userAccount.setUser(sysUser);
        userAccount.setGitReward(BigDecimal.ZERO);
        userAccount.setTrueReward(BigDecimal.ZERO);
        userAccount.setTtrReward(BigDecimal.ZERO);
        userAccountRepository.save(userAccount);
        return sysUser;
    }

    @Override
    public UserInfoDTO getUserInfo(long userId, Integer rewardType) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        SysUser sysUser = userRepository.findOne(userId);
//        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        if (sysUser == null) {
            throw new NullException("用户不存在");
        }
        userInfoDTO.setUser(sysUser);
        List<SysUser> recommendUserIds = userRepository.findByRecommendUserId(sysUser.getId());
        long count = 0;
        for (int i = 0; i < recommendUserIds.size(); i++) {
            SysUser user = recommendUserIds.get(i);
            //只记录审核通过的玩家
            if (user.getAuditStatus() == AuditStatusEnum.AUDITED.getCode()) {
                count++;
            }
        }
        userInfoDTO.setRecommendPeople(count);
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setGitReward("0");
        userAccountDTO.setTrueReward("0");
        userAccountDTO.setTtrReward("0");
        BsUserAccount userAccount = sysUser.getUserAccount();
        if (userAccount != null) {
            userAccountDTO.setGitReward(userAccount.getGitReward().toString());
            userAccountDTO.setTrueReward(userAccount.getTrueReward().toString());
            userAccountDTO.setTtrReward(userAccount.getTtrReward().toString());
            QBsUserAccountDetail accountDetail = QBsUserAccountDetail.bsUserAccountDetail;
            Iterable<BsUserAccountDetail> userAccountDetailIterable = userAccountDetailRepository.findAll(accountDetail.userAccount.eq(userAccount).and(accountDetail.rewardType.eq(rewardType)));
            List<BsUserAccountDetail> userAccountDetailList = new ArrayList<>();
            userAccountDetailIterable.forEach(x -> userAccountDetailList.add(x));
            Collections.sort(userAccountDetailList, new Comparator<BsUserAccountDetail>() {
                @Override
                public int compare(BsUserAccountDetail o1, BsUserAccountDetail o2) {
                    long id1 = o1.getId();
                    long id2 = o2.getId();
                    if (id1 == id2) {
                        return 0;
                    } else if (id1 > id2) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            userAccountDTO.setAccountDetails(userAccountDetailList);
        }
        userInfoDTO.setUserAccount(userAccountDTO);
        return userInfoDTO;
    }

    @Override
    public SysUser getByUserId(Long userId) {
        SysUser sysUser = userRepository.findOne(userId);
//        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        if (sysUser == null) {
            throw new NullException("用户不存在");
        }
        return sysUser;
    }

    @Override
    public List<RecommendTaskDTO> getRecommendUserList(long userId) {
        SysUser user = userRepository.findOne(userId);
//        Preconditions.checkArgument(null != user, "用户不存在");
        if (user == null) {
            throw new NullException("用户不存在");
        }
        List<SysUser> sysUserList = userRepository.findByRecommendUserId(user.getId());
        List<RecommendTaskDTO> recommendTaskDTOList = new ArrayList<>();
        for (int i = 0; i < sysUserList.size(); i++) {
            SysUser sysUser = sysUserList.get(i);
            if (sysUser.getAuditStatus() != AuditStatusEnum.AUDITED.getCode()) {
                continue;
            }
            RecommendTaskDTO recommendTaskDTO = new RecommendTaskDTO();
            recommendTaskDTO.setPersonName(sysUser.getPersonName());
            recommendTaskDTO.setRewardNum(sysUser.getLevel());
            recommendTaskDTO.setCreateTime(sysUser.getUpdateTime());
            recommendTaskDTOList.add(recommendTaskDTO);
        }
//        QBsUserAccountDetail qUserAccountDetail = QBsUserAccountDetail.bsUserAccountDetail;
//        Iterable<BsUserAccountDetail> userAccountDetailIterable = userAccountDetailRepository.findAll(qUserAccountDetail.userAccount.user.eq(user));
//        List<RecommendTaskDTO> recommendTaskDTOList = new ArrayList<>();
//        userAccountDetailIterable.forEach(x -> {
//            RecommendTaskDTO recommendTaskDTO = new RecommendTaskDTO();
//            recommendTaskDTO.setPersonName(x.getUserAccount().getUser().getPersonName());
//            recommendTaskDTO.setRewardNum(x.getRewardNum());
//            recommendTaskDTO.setCreateTime(x.getCreateTime());
//            recommendTaskDTOList.add(recommendTaskDTO);
//        });
        return recommendTaskDTOList;
    }
}
