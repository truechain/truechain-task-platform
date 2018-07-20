package com.truechain.task.api.model.dto;

import java.io.Serializable;

/**
 * 微信AccessTokenVo实体类
 */
public class JsapiTicketDTO implements Serializable {

    private static final long serialVersionUID = 11L;
    //微信ticket
    private String ticket;

    //微信token有效时间
    private String expires_in;

    //错误编码
    private String errcode;

    //错误描述
    private String errmsg;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

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
}
