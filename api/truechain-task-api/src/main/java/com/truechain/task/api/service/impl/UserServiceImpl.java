package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.UserAccountDTO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.repository.BsUserAccountDetailRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.BsUserAccount;
import com.truechain.task.model.entity.BsUserAccountDetail;
import com.truechain.task.model.entity.QBsUserAccountDetail;
import com.truechain.task.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private BsUserAccountDetailRepository userAccountDetailRepository;

    @Override
    public SysUser getUserByOpenId(String openId) {
        SysUser sysUser = userRepository.findByOpenId(openId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        return sysUser;
    }

    @Override
    public SysUser getUserByMobile(String mobile) {
        SysUser sysUser = userRepository.findByMobile(mobile);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
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
    public UserInfoDTO getUserInfo(long userId, Integer rewardType) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        userInfoDTO.setUser(sysUser);
        userInfoDTO.setRecommendPeople(3L);
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setGitReward("0");
        userAccountDTO.setTrueReward("0");
        userAccountDTO.setTtrReward("0");
        BsUserAccount userAccount = sysUser.getUserAccount();
        if (userAccount != null) {
            userAccountDTO.setGitReward(userAccount.getGitReward());
            userAccountDTO.setTrueReward(userAccount.getTrueReward());
            userAccountDTO.setTtrReward(userAccount.getTtrReward());
            QBsUserAccountDetail accountDetail = QBsUserAccountDetail.bsUserAccountDetail;
            Iterable<BsUserAccountDetail> userAccountDetailIterable = userAccountDetailRepository.findAll(accountDetail.userAccount.eq(userAccount).and(accountDetail.rewardType.eq(rewardType)));
            Set<BsUserAccountDetail> userAccountDetailList = new HashSet<>();
            userAccountDetailIterable.forEach(x -> userAccountDetailList.add(x));
            userAccountDTO.setAccountDetails(userAccountDetailList);
        }
        userInfoDTO.setUserAccount(userAccountDTO);
        return userInfoDTO;
    }

    @Override
    public List<RecommendTaskDTO> getRecommendUserList(long userId) {
        List<RecommendTaskDTO> recommendTaskDTOList = new ArrayList<>();
        RecommendTaskDTO recommendTaskDTO = new RecommendTaskDTO();
        recommendTaskDTO.setPersonName("小米1");
        recommendTaskDTO.setReward("100");
        recommendTaskDTO.setCreateTime("2018-06-22 11:24:57");
        recommendTaskDTOList.add(recommendTaskDTO);
        recommendTaskDTO = new RecommendTaskDTO();
        recommendTaskDTO.setPersonName("小米2");
        recommendTaskDTO.setReward("300");
        recommendTaskDTO.setCreateTime("2018-06-21 11:24:57");
        recommendTaskDTOList.add(recommendTaskDTO);
        return recommendTaskDTOList;
    }
}
