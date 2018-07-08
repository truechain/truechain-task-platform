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
    private List<UserAccountDTO> userAccountList;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<UserAccountDTO> getUserAccountList() {
        return userAccountList;
    }

    public void setUserAccountList(List<UserAccountDTO> userAccountList) {
        this.userAccountList = userAccountList;
    }
}
