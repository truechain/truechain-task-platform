package com.truechain.task.admin.model.dto;

import com.truechain.task.model.entity.SysUser;

import java.io.Serializable;

public class UserDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysUser sysUser;
    private String refererWXName;
    private String refererPhone;
    private String refererAddress;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getRefererWXName() {
        return refererWXName;
    }

    public void setRefererWXName(String refererWXName) {
        this.refererWXName = refererWXName;
    }

    public String getRefererPhone() {
        return refererPhone;
    }

    public void setRefererPhone(String refererPhone) {
        this.refererPhone = refererPhone;
    }

    public String getRefererAddress() {
        return refererAddress;
    }

    public void setRefererAddress(String refererAddress) {
        this.refererAddress = refererAddress;
    }
}
