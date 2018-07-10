package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.api.model.dto.UserAccountDTO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.BsUserAccount;
import com.truechain.task.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository userRepository;

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
    public UserInfoDTO getUserInfo(long userId) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        SysUser sysUser = userRepository.findOne(userId);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        userInfoDTO.setUser(sysUser);
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setGitReward("0");
        userAccountDTO.setTrueReward("0");
        userAccountDTO.setTtrReward("0");
        BsUserAccount userAccount = sysUser.getUserAccount();
        if (userAccount != null) {
            userAccountDTO.setGitReward(userAccount.getGitReward());
            userAccountDTO.setTrueReward(userAccount.getTrueReward());
            userAccountDTO.setTtrReward(userAccount.getTtrReward());
            userAccountDTO.setAccountDetails(userAccount.getAccountDetails());
        }
        return userInfoDTO;
    }
}
