package com.truechain.task.admin.service;

import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.model.dto.LoginDTO;

public interface AccountService {

    /**
     * 注册
     *
     * @param authUser
     * @return
     */
    AuthUser register(AuthUser authUser);

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    LoginDTO login(String userName, String password);

    /**
     * 刷新Token
     *
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);
}
