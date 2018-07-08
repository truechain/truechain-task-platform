package com.truechain.task.api.model.dto;

import com.truechain.task.model.entity.SysUser;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户详情
     */
    private SysUser user;
    /**
     * 用户账户
     */
    private UserAccountDTO userAccount;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public UserAccountDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccountDTO userAccount) {
        this.userAccount = userAccount;
    }
}
