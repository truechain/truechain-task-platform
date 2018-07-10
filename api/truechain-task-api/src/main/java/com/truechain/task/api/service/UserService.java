package com.truechain.task.api.service;


import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.model.entity.SysUser;

public interface UserService {

    /**
     * 根据OpenId获取用户
     *
     * @param openId
     * @return
     */
    SysUser getUserByOpenId(String openId);

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    SysUser getUserByMobile(String mobile);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    SysUser addUser(SysUser user);

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    UserInfoDTO getUserInfo(long userId);
}
