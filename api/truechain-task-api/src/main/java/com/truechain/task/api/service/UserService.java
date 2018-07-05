package com.truechain.task.api.service;


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
     * 添加用户
     *
     * @param user
     * @return
     */
    SysUser addUser(SysUser user);
}
