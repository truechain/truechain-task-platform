package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.UserService;
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
    public SysUser getUserByUserName(String userName) {
        SysUser sysUser = userRepository.findByUserName(userName);
        Preconditions.checkArgument(null != sysUser, "该用户不存在");
        return sysUser;
    }

    @Override
    public SysUser addUser(SysUser user) {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfo(long userId) {
        return null;
    }
}
