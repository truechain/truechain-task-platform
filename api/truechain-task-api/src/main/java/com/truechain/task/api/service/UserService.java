package com.truechain.task.api.service;


import com.truechain.task.api.model.dto.RecommendTaskDTO;
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
     * 获取用户详情
     *
     * @param userId
     * @param rewardType
     * @return
     */
    UserInfoDTO getUserInfo(long userId, Integer rewardType);

    /**
     * 获取推荐记录列表
     *
     * @param userId
     * @return
     */
    List<RecommendTaskDTO> getRecommendUserList(long userId);
}
