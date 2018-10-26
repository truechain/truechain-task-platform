package com.truechain.task.api.service;


import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.ReferrerDTO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.model.entity.SysUser;

import java.util.List;

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
     * 更新用户信息
     *
     * @param user
     * @return
     */
    SysUser updateUser(SysUser user);
    
    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    SysUser updateSimpleUser(SysUser user);

    /**
     * 获取用户详情
     *
     * @param userId
     * @param rewardType
     * @return
     */
    UserInfoDTO getUserInfo(long userId, Integer rewardType);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    SysUser getByUserId(Long userId);

    /**
     * 获取推荐记录列表
     *
     * @param userId
     * @return
     */
    List<RecommendTaskDTO> getRecommendUserList(long userId);
    
    
    /**
     * 根据推荐号获取推荐人信息
     *
     * @param code
     * @return
     */
    ReferrerDTO getReferrerByCode(String referralCode);
    
     /** 获取微信信息
     * @param code
     * @param userId
     */
    void getWxUserInfo(String code,Long userId);
}
