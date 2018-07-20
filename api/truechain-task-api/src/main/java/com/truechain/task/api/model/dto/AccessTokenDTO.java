package com.truechain.task.api.model.dto;

import java.io.Serializable;

/**
 * 微信AccessTokenVo实体类
 */
public class AccessTokenDTO implements Serializable {

    private static final long serialVersionUID = 11L;
    //微信token
    private String access_token;

    //微信token有效时间
    private String expires_in;

    //错误编码
    private String errcode;

    //错误描述
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
